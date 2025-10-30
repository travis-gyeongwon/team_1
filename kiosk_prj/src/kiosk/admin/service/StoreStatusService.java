package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.admin.dao.StoreStatusDAO;

public class StoreStatusService {
	public String searchStoreStatus(String id) throws SQLException, IOException, ClassNotFoundException {
		String flagStr = "";
		StoreStatusDAO ssDAO = StoreStatusDAO.getInstance();
		flagStr = ssDAO.selectStoreStatus(id);
		
		if("".equals(flagStr)) {
			
		}
		
		return flagStr;
	}
	
	public int modifyStoreStatus(String openStatus, String id) {
		int cnt = 0;
		StoreStatusDAO ssDAO = StoreStatusDAO.getInstance();
		try {
			cnt = ssDAO.updateStoreStatus(openStatus, id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			cnt = -1;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(cnt > 1) cnt = 1;
		return cnt;
	}
	
	public boolean checkOrderList() {
		//처리되지 않은 주문 내역이 있는지 확인
		boolean flag = false;
		
		StoreStatusDAO ssDAO = StoreStatusDAO.getInstance();
		try {
			flag = ssDAO.selectOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
