package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.EndDAO;

public class EndService {
	
	public void changeInventory(String menuname)  {
		
		try {
			EndDAO.getInstance().updateInventory(menuname);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
	}//changeInvenTory
	
	public void changeOrderStatus(String orderNum) {
		
		try {
			EndDAO.getInstance().updateOrderStatus(orderNum);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}//changeOrderStatus

}//class
