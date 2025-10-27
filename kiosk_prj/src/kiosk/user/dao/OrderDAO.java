package kiosk.user.dao;

import java.io.IOException;
import java.sql.Blob; // BLOB 처리를 위해 import
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
	 * (판매중이고, 삭제되지 않은 메뉴)
	 * @param category "커피" 또는 "논커피" (DB의 category_text 값)
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
				SELECT 
				    m.menu_name, m.price, m.delete_flag, c.category_text, m.menu_img, 
				    NVL(i.quantity, 0) as quantity
				FROM 
				    Menu m 
				    JOIN CATEGORY_OPTION co ON m.menu_name = co.menu_name
				    JOIN CATEGORY c ON co.category_code = c.category_code
				    LEFT JOIN Inventory i ON m.menu_name = i.menu_name
				WHERE 
				    c.category_text = ? AND m.delete_flag = 'N' 
				ORDER BY 
				    m.menu_add_at ASC
				""";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, category); 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderMenuDTO dto = new OrderMenuDTO();
				dto.setMenuName(rs.getString("menu_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setDeleteFlag(rs.getString("delete_flag"));
				dto.setCategory(rs.getString("category_text")); 
				Blob blob = rs.getBlob("menu_img");
				if (blob != null) {
					byte[] imageBytes = blob.getBytes(1, (int) blob.length());
					dto.setMenuImage(imageBytes); 
				} else {
					dto.setMenuImage(null);
				}
				
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
	 * (스키마와 일치하므로 수정 없음)
	 */
	public int insertOrder(Connection con, String orderNum) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			String sql = "INSERT INTO ORDER_LIST (ORDER_NUM, ORDER_TIME, PHONE, TAKEOUT_FLG, CHECKOUT_TYPECODE, STATUS_CODE) VALUES (?, SYSTIMESTAMP, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderNum);
			pstmt.setString(2, null); 
			pstmt.setString(3, "N"); 
			pstmt.setInt(4, 1);    
			pstmt.setInt(5, 1);     
			result = pstmt.executeUpdate();

		} finally {
			if (pstmt != null) { pstmt.close(); }
		}
		return result;
	}

	/**
	 * Order_Product 테이블에 주문 상품 리스트를 저장합니다. (Batch Insert 사용)
	 * (스키마와 일치하므로 수정 없음)
	 */
	public int[] insertOrderProducts(Connection con, String orderNum, List<OrderProductDTO> productList) throws SQLException {
		PreparedStatement pstmt = null;
		int[] results = null;

		try {
			String sql = """
			    INSERT INTO ORDER_DETAIL
			    (ORDER_DETAIL_NUM, ORDER_NUM, MENU_NAME, TEMP_OPTION, SIZE_OPTION, SHOT_OPTION, AMOUNT, ORDER_PRICE)
			    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
			    """;
			pstmt = con.prepareStatement(sql);

			int detailSeq = 1; 
			for (OrderProductDTO dto : productList) {
				String orderDetailNum = String.format("%s_%02d", orderNum, detailSeq++);

				pstmt.setString(1, orderDetailNum);
				pstmt.setString(2, orderNum);
				pstmt.setString(3, dto.getMenuName());
				pstmt.setInt(4, mapTempOptionToCode(dto.getTempOption())); 
				pstmt.setInt(5, mapSizeOptionToCode(dto.getSizeOption()));
				pstmt.setInt(6, mapShotOptionToCode(dto.getExtraOption()));

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
		return 2; 
	}
	private int mapSizeOptionToCode(String sizeOption) {
		if ("Medium".equalsIgnoreCase(sizeOption) || "Regular".equalsIgnoreCase(sizeOption)) return 1;
		if ("Large".equalsIgnoreCase(sizeOption)) return 2;
		return 1;
	}
	private int mapShotOptionToCode(String shotOption) {
		if ("기본".equals(shotOption)) return 1;
		if ("연하게".equals(shotOption)) return 2;
		if ("샷추가".equals(shotOption)) return 3;
		return 1;
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
			        m.menu_name, m.price, m.delete_flag, c.category_text, m.menu_img, 
			        NVL(i.quantity, 0) as quantity
			    FROM 
			        Menu m 
			        LEFT JOIN CATEGORY_OPTION co ON m.menu_name = co.menu_name
			        LEFT JOIN CATEGORY c ON co.category_code = c.category_code
			        LEFT JOIN Inventory i ON m.menu_name = i.menu_name
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
				dto.setCategory(rs.getString("category_text"));
				Blob blob = rs.getBlob("menu_img");
				if (blob != null) {
					byte[] imageBytes = blob.getBytes(1, (int) blob.length());
					dto.setMenuImage(imageBytes); 
				} else {
					dto.setMenuImage(null);
				}
				
				dto.setQuantity(rs.getInt("quantity"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			db.dbClose(con, pstmt, rs);
		}
		return dto;
	}
	
	/**
	 * @param con (Service에서 전달받은 Connection)
	 * @return "yymmddNNNN" 형식의 마지막 주문번호. 오늘 주문이 없었다면 null.
	 * @throws SQLException
	 */
	public String selectMaxOrderNumToday(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String maxOrderNum = null;
		String today = new java.text.SimpleDateFormat("yyMMdd").format(new java.util.Date());
		String sql = "SELECT MAX(ORDER_NUM) FROM ORDER_LIST WHERE ORDER_NUM LIKE ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, today + "%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				maxOrderNum = rs.getString(1); 
			}
		} finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
		return maxOrderNum;
	}
	
}