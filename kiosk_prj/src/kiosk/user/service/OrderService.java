package kiosk.user.service;

import java.io.IOException;
import java.sql.Connection;       
import java.sql.SQLException;     
import java.text.SimpleDateFormat;  
import java.util.Date;            
import java.util.List;

import kiosk.user.dao.GetConnection; 
import kiosk.user.dao.OrderDAO;
import kiosk.user.dto.OrderMenuDTO;
import kiosk.user.dto.OrderProductDTO; 

public class OrderService {

	private static OrderService instance;

	private OrderService() {
	}

	public static OrderService getInstance() {
		if (instance == null) {
			instance = new OrderService();
		}
		return instance;
	}

	public List<OrderMenuDTO> getMenus(String category) {
		return OrderDAO.getInstance().selectMenusByCategory(category);
	}

	public OrderMenuDTO getMenuByName(String menuName) {
	    return OrderDAO.getInstance().getMenuByName(menuName);
	}

	public boolean saveOrder(List<OrderProductDTO> shoppingCart) {
		boolean success = false;
		GetConnection db = GetConnection.getInstance(); // DB 연결 객체
		Connection con = null;
		OrderDAO dao = OrderDAO.getInstance();

		try {
			con = db.getConnection(); 
			con.setAutoCommit(false); 
			String lastOrderNum = dao.selectMaxOrderNumToday(con); 		
			String newOrderNum;
			String today = new SimpleDateFormat("yyMMdd").format(new Date()); // "251027"
			
			if (lastOrderNum == null) {
				newOrderNum = today + "0001";
			} else {
				String seqPart = lastOrderNum.substring(6); 
				int seqInt = Integer.parseInt(seqPart);     
				seqInt++;
				newOrderNum = today + String.format("%04d", seqInt); 
			}
		
			int orderResult = dao.insertOrder(con, newOrderNum);		
			int[] productResults = dao.insertOrderProducts(con, newOrderNum, shoppingCart);
			boolean allProductsInserted = true;
			if (productResults != null) { 
				for (int result : productResults) {
					if (result == java.sql.Statement.EXECUTE_FAILED) {
						allProductsInserted = false;
						break;
					}
				}
			} else {
				allProductsInserted = false;
			}
			if (orderResult == 1 && allProductsInserted) {
				con.commit();
				success = true;
			} else {
				con.rollback();
				System.err.println("주문 저장 실패: Rollback 실행. orderResult=" + orderResult + ", allProductsInserted=" + allProductsInserted);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
				try { con.rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
			}
		} catch (IOException e) { 
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true); 
					con.close(); 
				} catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return success;
	}
}