package kiosk.user.dao;

import java.io.IOException;
import java.io.InputStream; // InputStream import 추가
import java.sql.Blob; // Blob import 추가
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections; // Collections import 추가
import java.util.List;

// ❗️ 수정된 OrderMenuDTO 사용 (byte[] menuImage, allowed options 포함)
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
	 * DB 연동 로직 - BLOB 이미지 처리 및 허용 옵션 조회 추가
	 * 카테고리별 메뉴 리스트를 DB에서 조회합니다.
	 * @param category "커피" 또는 "논커피" (DB의 category_text 값)
	 * @return 메뉴 DTO 리스트 (byte[] 이미지, 허용 옵션 목록 포함)
	 */
	public List<OrderMenuDTO> selectMenusByCategory(String category) { // category 파라미터 이름 유지
		List<OrderMenuDTO> list = new ArrayList<>();
		GetConnection db = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = db.getConnection(); // GetConnection 사용

			// SQL 쿼리: menu_img 조회, category 테이블 JOIN (보내주신 버전 사용)
			String sql = """
				SELECT
				    m.menu_name, m.price, m.status, m.delete_flag, c.category_text, m.menu_img,
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
				String currentMenuName = rs.getString("menu_name"); // 옵션 조회 위해 미리 저장
				dto.setMenuName(currentMenuName);
				dto.setPrice(rs.getInt("price"));
				dto.setStatus(rs.getString("status")); // status 설정
				dto.setDeleteFlag(rs.getString("delete_flag"));
				dto.setCategory(rs.getString("category_text")); // category_text 설정
				dto.setQuantity(rs.getInt("quantity"));

				// BLOB 데이터 처리 -> byte[] 변환
				Blob blob = rs.getBlob("menu_img");
				byte[] imageBytes = null;
				if (blob != null) {
					try {
					    imageBytes = blob.getBytes(1, (int) blob.length());
					} catch (SQLException e) {
					    System.err.println("이미지 BLOB getBytes 오류 (" + currentMenuName + "): " + e.getMessage());
					}
				}
				dto.setMenuImage(imageBytes); // byte[] 설정

				// ❗️ 허용된 옵션 코드 목록 조회 및 설정 (Connection 재활용)
				dto.setAllowedTempCodes(getAllowedOptionCodes(con, "SELECT temp_code FROM TEMP_OPTION WHERE menu_name = ?", currentMenuName));
				dto.setAllowedSizeCodes(getAllowedOptionCodes(con, "SELECT size_code FROM SIZE_OPTION WHERE menu_name = ?", currentMenuName));
				dto.setAllowedShotCodes(getAllowedOptionCodes(con, "SELECT shot_code FROM SHOT_OPTION WHERE menu_name = ?", currentMenuName));

				list.add(dto);
			}

		} catch (IOException e) { // getConnection 용
			e.printStackTrace();
		} catch (SQLException e) { // 나머지 SQL 용
			e.printStackTrace();
		} finally {
			// dbClose 호출 (SQLException 처리)
				db.dbClose(con, pstmt, rs);
		}
		return list;
	}


	/**
	 * 메뉴 이름으로 메뉴 하나의 상세 정보(BLOB 이미지 + 허용 옵션)를 DB에서 조회
	 */
	public OrderMenuDTO getMenuByName(String menuName) {
		OrderMenuDTO dto = null;
		GetConnection db = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = db.getConnection();
			// 1. 기본 메뉴 정보 조회 (menu_img 포함)
			String menuSql = """
			    SELECT
			        m.menu_name, m.price, m.status, m.delete_flag, c.category_text, m.menu_img,
			        NVL(i.quantity, 0) as quantity
			    FROM
			        Menu m
			        LEFT JOIN CATEGORY_OPTION co ON m.menu_name = co.menu_name
			        LEFT JOIN CATEGORY c ON co.category_code = c.category_code
			        LEFT JOIN Inventory i ON m.menu_name = i.menu_name
			    WHERE
			        m.menu_name = ?
			    """;
			pstmt = con.prepareStatement(menuSql);
			pstmt.setString(1, menuName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new OrderMenuDTO();
				dto.setMenuName(rs.getString("menu_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setStatus(rs.getString("status")); // status 설정
				dto.setDeleteFlag(rs.getString("delete_flag"));
				dto.setCategory(rs.getString("category_text")); // category_text 설정
				dto.setQuantity(rs.getInt("quantity"));

				// BLOB 처리 -> byte[]
				Blob blob = rs.getBlob("menu_img");
				byte[] imageBytes = null;
				if (blob != null) {
					try {
						imageBytes = blob.getBytes(1, (int) blob.length());
					} catch (SQLException e) {
						System.err.println("이미지 BLOB getBytes 오류 (" + menuName + "): " + e.getMessage());
					}
				}
				dto.setMenuImage(imageBytes); // byte[] 설정

				// ❗️ 2. 허용된 옵션 코드 목록 조회 및 설정 (pstmt, rs 재사용 전 닫기)
				if (pstmt != null) pstmt.close();
				if (rs != null) rs.close();
				pstmt = null; rs = null; // 초기화 필수

				dto.setAllowedTempCodes(getAllowedOptionCodes(con, "SELECT temp_code FROM TEMP_OPTION WHERE menu_name = ?", menuName));
				dto.setAllowedSizeCodes(getAllowedOptionCodes(con, "SELECT size_code FROM SIZE_OPTION WHERE menu_name = ?", menuName));
				dto.setAllowedShotCodes(getAllowedOptionCodes(con, "SELECT shot_code FROM SHOT_OPTION WHERE menu_name = ?", menuName));

			} // if (rs.next()) 끝
		} catch (IOException e) { // getConnection 용
			e.printStackTrace();
		} catch (SQLException e) { // 나머지 SQL 용
			e.printStackTrace();
		} finally {
			// 최종 자원 해제 (null 체크 필수)
				db.dbClose(con, pstmt, rs);
		}
		return dto;
	}

	/**
	 * [헬퍼] 특정 메뉴에 허용된 옵션 코드 목록을 조회 (리소스 자체 관리)
	 */
	private List<Integer> getAllowedOptionCodes(Connection con, String sql, String menuName) throws SQLException {
		List<Integer> codes = new ArrayList<>();
		PreparedStatement pstmtOption = null; // 새 PreparedStatement
		ResultSet rsOption = null;       // 새 ResultSet
		try {
			pstmtOption = con.prepareStatement(sql);
			pstmtOption.setString(1, menuName);
			rsOption = pstmtOption.executeQuery();
			while (rsOption.next()) {
				codes.add(rsOption.getInt(1));
			}
		} finally {
			// 여기서 사용한 PreparedStatement와 ResultSet만 닫음 (null 체크 후)
			if (rsOption != null) { try { rsOption.close(); } catch (SQLException e) { /* ignored */ } }
			if (pstmtOption != null) { try { pstmtOption.close(); } catch (SQLException e) { /* ignored */ } }
		}
		System.out.println("DAO - getAllowedOptionCodes for '" + menuName + "' ("+sql.substring(7, 16).trim()+"): " + codes); // 확인용
		// 조회된 코드가 없으면 빈 리스트 반환
		return codes.isEmpty() ? Collections.emptyList() : codes;
	}


	/**
	 * Orders 테이블(여기서는 ORDER_LIST)에 메인 주문 정보를 저장합니다.
	 * (보내주신 코드와 동일)
	 */
	public int insertOrder(Connection con, String orderNum) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String sql = "INSERT INTO ORDER_LIST (ORDER_NUM, ORDER_TIME, PHONE, TAKEOUT_FLG, CHECKOUT_TYPECODE) VALUES (?, SYSTIMESTAMP, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderNum);
			pstmt.setString(2, null);
			pstmt.setString(3, "N");
			pstmt.setInt(4, 1);
			result = pstmt.executeUpdate();
		} finally {
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { /* ignored */ } }
		}
		return result;
	}


	/**
	 * ORDER_DETAIL 테이블에 주문 상품 리스트를 저장합니다. (Batch Insert 사용)
	 * (보내주신 코드와 동일)
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
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { /* ignored */ } }
		}
		return results;
	}


	// --- 옵션 코드 매핑 헬퍼 메서드 ---
	// (보내주신 코드와 동일)
	private int mapTempOptionToCode(String tempOption) {
		if ("HOT".equalsIgnoreCase(tempOption)) return 1;
		if ("ICE".equalsIgnoreCase(tempOption)) return 2;
		System.err.println("경고: 알 수 없는 온도 옵션 '" + tempOption + "', 기본값 2(ICE) 사용");
		return 2;
	}
	private int mapSizeOptionToCode(String sizeOption) {
		// DTO에는 "Medium"으로 저장되므로 "Regular" 체크 불필요 -> 필요함 (보내주신 코드 기준)
		if ("Medium".equalsIgnoreCase(sizeOption) || "Regular".equalsIgnoreCase(sizeOption)) return 1;
		if ("Large".equalsIgnoreCase(sizeOption)) return 2;
		System.err.println("경고: 알 수 없는 사이즈 옵션 '" + sizeOption + "', 기본값 1(Regular) 사용");
		return 1;
	}
	private int mapShotOptionToCode(String shotOption) {
		if ("기본".equals(shotOption)) return 1;
		if ("연하게".equals(shotOption)) return 2;
		if ("샷추가".equals(shotOption)) return 3;
		System.err.println("경고: 알 수 없는 샷 옵션 '" + shotOption + "', 기본값 1(기본) 사용");
		return 1;
	}


	/**
	 * 특정 날짜(YYMMDD)의 주문 번호 중 가장 큰 순번을 조회합니다.
	 * (보내주신 코드 기반, Connection 파라미터 필요)
	 */
	public int getMaxOrderSequenceForDate(Connection con, String datePart) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxSequence = 0;
		String likePattern = datePart + "%";
		try {
			// SQL 쿼리 수정: selectMaxOrderNumToday 기반 -> 순번 부분만 추출
			String sql = "SELECT NVL(MAX(TO_NUMBER(SUBSTR(order_num, 7, 4))), 0) FROM order_list WHERE order_num LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, likePattern);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxSequence = rs.getInt(1);
			}
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { /* ignored */ } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { /* ignored */ } }
		}
		System.out.println("DAO - getMaxOrderSequenceForDate(" + datePart + "): " + maxSequence);
		return maxSequence;
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
	
