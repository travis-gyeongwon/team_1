package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.admin.dao.StoreStatusDAO;
import kiosk.admin.dto.StoreStatusDTO;

public class StoreStatusService {
	public String searchStoreStatus(String id) throws SQLException, IOException, ClassNotFoundException {
		String flagStr = "N";
		StoreStatusDAO ssDAO = StoreStatusDAO.getInstance();
		StoreStatusDTO ssDTO = ssDAO.selectStoreStatus(id);
		
		if(ssDTO != null) {
			flagStr = ssDTO.getStatus();
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
}
