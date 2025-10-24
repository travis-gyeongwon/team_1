package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.admin.dao.DetailOrderDisplayDAO;
import kiosk.admin.dao.OrderManageDAO;
import kiosk.admin.dto.DetailOrderDisplayOrderDTO;

public class DetailOrderDisplayService {
	
	public DetailOrderDisplayService() {
		
	}// DetailOrderDisplayService
	
	public DetailOrderDisplayOrderDTO searchOneMakingDetailOrder(String orderNum) {
		DetailOrderDisplayOrderDTO dodoDTO = null;
		
		DetailOrderDisplayDAO dodDAO = DetailOrderDisplayDAO.getInstance();
		try {
			dodoDTO = dodDAO.selecteOneMakingOrder(orderNum);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dodoDTO;
	}// searchOneMakingDetailOrder
	
	public int modifyOrderStatus(String orderNum) {
		int flag = 0;
		
		DetailOrderDisplayDAO dodDAO = DetailOrderDisplayDAO.getInstance();
		try {
			flag = dodDAO.updateOrderStatus(orderNum);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
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
