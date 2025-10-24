package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.admin.dao.OrderManageDAO;
import kiosk.admin.dto.OrderManageMakeOrderDTO;

public class OrderManageService {
	public OrderManageService() {
		
	}// OrderManageService
	
	public List<OrderManageMakeOrderDTO> searchAllMakingOrder(){
		List<OrderManageMakeOrderDTO> listMakeOrder = new ArrayList<OrderManageMakeOrderDTO>();
		
		OrderManageDAO omDAO = OrderManageDAO.getInstance();
		try {
			listMakeOrder = omDAO.selectAllMakingOrder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// end catch
				
		
		return listMakeOrder;
	}// searchAllMakingOrder
	
	public int modifyOrderStatus(String orderNum) {
		int flag = 0;
		
		OrderManageDAO omDAO = OrderManageDAO.getInstance();
		try {
			flag = omDAO.updateOrderStatus(orderNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}// modifyOrderStatus
	
	
}// class
