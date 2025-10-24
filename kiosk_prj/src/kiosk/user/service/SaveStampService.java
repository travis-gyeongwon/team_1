package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.SaveStampDAO;

public class SaveStampService {
	
	public SaveStampService() {
	}// SaveStampService
	
	public int searchMember(String phone) {
		int flag = 0;
		
		try {
			SaveStampDAO ssDAO = SaveStampDAO.getInstance();
			flag = ssDAO.selectMember(phone);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch
		
		return flag;
	}// searchMember

}// class
