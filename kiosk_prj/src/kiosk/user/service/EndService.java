package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.EndDAO;

public class EndService {
	
	public void changeInventory(String menuName)  {
		
			try {
				EndDAO.getInstance().updateInventory(menuName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
