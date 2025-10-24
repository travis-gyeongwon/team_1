package kiosk.user.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO;

public class OrderDAO {

	private static OrderDAO instance;

	private OrderDAO() {
	}

	public static OrderDAO getInstance() {
		if (instance == null) {
			instance = new OrderDAO();
		}
		return instance;
	}

	/**
	 * DB 연동 로직
	 * 카테고리별 메뉴 리스트를 DB에서 조회합니다.
	 * (판매중이고, 삭제되지 않은 메뉴 12개)
	 * @param category "Coffee" 또는 "Beverage"
	 * @return 메뉴 DTO 리스트
	 */
	public List<OrderMenuDTO> selectMenusByCategory(String category) {
		List<OrderMenuDTO> list = new ArrayList<>();
		

		GetConnection db = GetConnection.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = db.getConnection(); 
		
			String sql = """
				SELECT * FROM (
				    SELECT 
				        m.menu_name, m.price, m.delete_flag, m.category, m.image_url, 
				        NVL(i.quantity, 0) as quantity -- 재고 테이블에 없으면 0으로 처리
				    FROM 
				        Menu m LEFT JOIN Inventory i ON m.menu_name = i.menu_name -- menu_name으로 JOIN (컬럼명 확인 필요)
				    WHERE 
				        m.category = ? AND m.delete_flag = 'N' 
				    ORDER BY 
				        m.menu_add_at ASC
				        )
				""";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, category); 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderMenuDTO dto = new OrderMenuDTO();
				dto.setMenuName(rs.getString("menu_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setDeleteFlag(rs.getString("delete_flag"));
				dto.setCategory(rs.getString("category"));
				dto.setImageUrl(rs.getString("image_url"));
				
				dto.setQuantity(rs.getInt("quantity"));
				
				list.add(dto); 
			}

		}catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			db.dbClose(con, pstmt, rs);
		}
		
		return list; 
	}

	/**
	 * Orders 테이블에 메인 주문 정보를 저장합니다.
	 * @param con DB Connection (Service에서 트랜잭션 관리를 위해 받아옴)
	 * @param orderNum 생성된 주문 번호
	 * @param totalPrice 총 주문 금액
	 * @return INSERT 성공 시 1, 실패 시 0
	 * @throws SQLException
	 */
	public int insertOrder(Connection con, String orderNum /* , int totalPrice */) throws SQLException { // totalPrice 파라미터 제거
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			// ❗️ 테이블명, 컬럼명 수정 (phone, takeout_flg, checkout_typecode, status_code는 임시값 또는 null)
			String sql = "INSERT INTO ORDER_LIST (ORDER_NUM, ORDER_TIME, PHONE, TAKEOUT_FLG, CHECKOUT_TYPECODE, STATUS_CODE) VALUES (?, SYSTIMESTAMP, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderNum);
			pstmt.setString(2, null); // TODO: 전화번호 받아오는 로직 필요 시 추가
			pstmt.setString(3, "N"); // TODO: 포장 여부 받아오는 로직 필요 시 추가 (기본값 'N')
			pstmt.setInt(4, 1);     // TODO: 결제 방법 코드 받아오는 로직 필요 시 추가 (기본값 1 '카드')
			pstmt.setInt(5, 1);     // TODO: 초기 주문 상태 코드 설정 (기본값 1 '확인 대기 중')

			result = pstmt.executeUpdate();

		} finally {
			if (pstmt != null) { pstmt.close(); }
		}
		return result;
	}

	/**
	 * Order_Product 테이블에 주문 상품 리스트를 저장합니다. (Batch Insert 사용)
	 * @param con DB Connection (Service에서 트랜잭션 관리를 위해 받아옴)
	 * @param orderNum 메인 주문 번호
	 * @param productList 저장할 상품 DTO 리스트 (shoppingCart)
	 * @return 각 INSERT 문의 성공 여부 배열 (Batch 처리 결과)
	 * @throws SQLException
	 */
	public int[] insertOrderProducts(Connection con, String orderNum, List<OrderProductDTO> productList) throws SQLException {
		PreparedStatement pstmt = null;
		int[] results = null;

		try {
			// ❗️ 테이블명, 컬럼명 수정
			// ❗️ ORDER_DETAIL_NUM 생성 방식 필요 (예: 주문번호 + 순번 조합)
			// ❗️ TEMP/SIZE/SHOT 옵션은 DTO의 문자열을 NUMBER 코드로 변환 필요
			String sql = """
			    INSERT INTO ORDER_DETAIL
			    (ORDER_DETAIL_NUM, ORDER_NUM, MENU_NAME, TEMP_OPTION, SIZE_OPTION, SHOT_OPTION, AMOUNT, ORDER_PRICE)
			    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
			    """;
			pstmt = con.prepareStatement(sql);

			int detailSeq = 1; // 주문 내 상세 순번
			for (OrderProductDTO dto : productList) {
				// ❗️ ORDER_DETAIL_NUM 생성 (예: "2510230001_01")
				String orderDetailNum = String.format("%s_%02d", orderNum, detailSeq++);

				pstmt.setString(1, orderDetailNum);
				pstmt.setString(2, orderNum);
				pstmt.setString(3, dto.getMenuName());

				// ❗️ 옵션 문자열 -> 코드로 변환 (매핑 로직 필요)
				pstmt.setInt(4, mapTempOptionToCode(dto.getTempOption())); // 예: "ICE" -> 2
				pstmt.setInt(5, mapSizeOptionToCode(dto.getSizeOption())); // 예: "Large" -> 2
				pstmt.setInt(6, mapShotOptionToCode(dto.getExtraOption())); // 예: "샷추가" -> 3

				pstmt.setInt(7, dto.getAmount());
				pstmt.setInt(8, dto.getOrderPrice());

				pstmt.addBatch();
			}

			results = pstmt.executeBatch();

		} finally {
			if (pstmt != null) { pstmt.close(); }
		}
		return results;
	}
	
	private int mapTempOptionToCode(String tempOption) {
		if ("HOT".equalsIgnoreCase(tempOption)) return 1;
		if ("ICE".equalsIgnoreCase(tempOption)) return 2;
		return 2; // 기본값 또는 오류 처리
	}
	private int mapSizeOptionToCode(String sizeOption) {
		if ("Medium".equalsIgnoreCase(sizeOption) || "Regular".equalsIgnoreCase(sizeOption)) return 1;
		if ("Large".equalsIgnoreCase(sizeOption)) return 2;
		return 1; // 기본값
	}
	private int mapShotOptionToCode(String shotOption) {
		if ("기본".equals(shotOption)) return 1;
		if ("연하게".equals(shotOption)) return 2;
		if ("샷추가".equals(shotOption)) return 3;
		return 1; // 기본값
	}
	
	

	/**
	 * 메뉴 이름으로 메뉴 하나의 상세 정보를 DB에서 조회
	 * @param menuName
	 * @return OrderMenuDTO
	 */
	public OrderMenuDTO getMenuByName(String menuName) {
		OrderMenuDTO dto = null;
		GetConnection db = GetConnection.getInstance(); 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = db.getConnection(); 
			String sql = """
			    SELECT 
			        m.menu_name, m.price, m.delete_flag, m.category, m.image_url, 
			        NVL(i.quantity, 0) as quantity
			    FROM 
			        Menu m LEFT JOIN Inventory i ON m.menu_name = i.menu_name
			    WHERE 
			        m.menu_name = ? 
			    """;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, menuName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new OrderMenuDTO(); 
				dto.setMenuName(rs.getString("menu_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setDeleteFlag(rs.getString("delete_flag"));
				dto.setCategory(rs.getString("category"));
				dto.setImageUrl(rs.getString("image_url"));
				dto.setQuantity(rs.getInt("quantity"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			db.dbClose(con, pstmt, rs);
		}
		return dto;
	}
	
}