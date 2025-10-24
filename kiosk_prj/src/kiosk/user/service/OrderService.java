package kiosk.user.service;

import java.sql.Connection;       // ❗️ import 추가
import java.sql.SQLException;     // ❗️ import 추가
import java.text.SimpleDateFormat;  // ❗️ import 추가
import java.util.Date;            // ❗️ import 추가
import java.util.List;

import kiosk.user.dao.GetConnection; // ❗️ import 추가 (DB 연결용)
import kiosk.user.dao.OrderDAO;
import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO; // ❗️ import 추가 (장바구니 항목용)

public class OrderService {

	private static OrderService instance;
	private static int dailyOrderSequence = 0; // 날짜별 순번
	private static String lastOrderDate = ""; // 마지막 주문 날짜
	
	// ❗️ 주문 번호 생성을 위한 순번 (int 사용)
	private static int orderSequence = 0;

	
	// 외부 생성 차단
	private OrderService() {
	}

	// Singleton 인스턴스 반환
	public static OrderService getInstance() {
		if (instance == null) {
			instance = new OrderService();
		}
		return instance;
	}

	/**
	 * 특정 카테고리의 메뉴 리스트를 DAO로부터 받아온다.
	 * @param category "Coffee" 또는 "Beverage"
	 * @return 메뉴 DTO 리스트
	 */
	public List<OrderMenuDTO> getMenus(String category) {
		return OrderDAO.getInstance().selectMenusByCategory(category);
	}

	/**
	 * 메뉴 이름으로 하나의 메뉴 정보를 DAO로부터 받아온다.
	 * @param menuName 조회할 메뉴 이름
	 * @return OrderMenuDTO
	 */
	public OrderMenuDTO getMenuByName(String menuName) {
	    return OrderDAO.getInstance().getMenuByName(menuName);
	}

	/**
	 * 장바구니에 담긴 상품 리스트를 DB에 주문 정보로 저장합니다. (트랜잭션 처리)
	 * @param shoppingCart OrderDesign에서 전달받은 장바구니 리스트
	 * @return 주문 저장 성공 여부 (true/false)
	 */
	public boolean saveOrder(List<OrderProductDTO> shoppingCart) {
		boolean success = false;
		GetConnection db = GetConnection.getInstance(); // DB 연결 객체
		Connection con = null;

		try {
			con = db.getConnection(); 
			con.setAutoCommit(false);

			// 3. 주문 번호 생성
			String orderNum = generateOrderNumber();

			// 4. 총 금액 계산
			int totalPrice = 0;
			for (OrderProductDTO dto : shoppingCart) {
				totalPrice += dto.getOrderPrice();
			}

			// 5. DAO 호출: Orders 테이블에 INSERT
			OrderDAO dao = OrderDAO.getInstance();
			int orderResult = dao.insertOrder(con, orderNum);

			// 6. DAO 호출: Order_Product 테이블에 Batch INSERT
			int[] productResults = dao.insertOrderProducts(con, orderNum, shoppingCart);

			// 7. 결과 확인 (모든 작업이 성공했는지)
			boolean allProductsInserted = true;
			if (productResults != null) { // Batch 결과가 null이 아닐 때만 확인
				for (int result : productResults) {
					// executeBatch 결과는 성공 시 1 이상 또는 Statement.SUCCESS_NO_INFO (-2)
					// 실패 시 Statement.EXECUTE_FAILED (-3)
					if (result == java.sql.Statement.EXECUTE_FAILED) {
						allProductsInserted = false;
						break;
					}
				}
			} else {
				// Batch 실행 결과 자체가 null이면 실패로 간주
				allProductsInserted = false;
			}


			// 8. 모든 INSERT가 성공했으면 Commit (DB에 최종 반영)
			if (orderResult == 1 && allProductsInserted) {
				con.commit();
				success = true;
				System.out.println("주문 저장 성공: " + orderNum);
			} else {
				// 9. 하나라도 실패했으면 Rollback (모든 작업 취소)
				con.rollback();
				System.err.println("주문 저장 실패: Rollback 실행. orderResult=" + orderResult + ", allProductsInserted=" + allProductsInserted);
			}

		} catch (SQLException e) {
			// 10. SQL 예외 발생 시 Rollback
			e.printStackTrace();
			if (con != null) {
				try { con.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
			}
		} finally {
			// 11. 트랜잭션 종료 (Auto Commit 다시 활성화) 및 연결 닫기
			if (con != null) {
				try {
					con.setAutoCommit(true);
					// ❗️ GetConnection의 dbClose는 Connection만 닫지 않으므로 직접 닫음
					con.close();
				} catch (SQLException e) { e.printStackTrace(); }
			}
		}

		return success;
	}

	/**
	 * 간단한 주문 번호 생성 로직 (날짜 + 순번) - int 사용
	 * @return 예: "Order_20251022_001"
	 */
	private String generateOrderNumber() {
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyMMdd"); // YYMMDD (6자리)
		String currentDate = dateSdf.format(new Date());

		// 날짜가 바뀌었으면 순번 리셋
		// (lastOrderDate와 dailyOrderSequence 필드가 클래스 레벨에 선언되어 있어야 함)
		if (!currentDate.equals(lastOrderDate)) {
			lastOrderDate = currentDate;
			dailyOrderSequence = 0;
		}

		int sequence = ++dailyOrderSequence; // 순번 1 증가
		// 예: "251024" + "0001" (4자리로 포맷) => "2510240001" (총 10자리)
		return String.format("%s%04d", currentDate, sequence);
	}

} // class 끝