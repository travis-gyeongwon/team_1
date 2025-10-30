package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.admin.dao.AdminLoginDAO;
import kiosk.admin.dto.LoginDTO;

public class AdminLoginService {
	private LoginDTO lDTO;
	
	public LoginDTO searchLogin(String id, String pw) {
		lDTO = null;
		AdminLoginDAO alDAO = AdminLoginDAO.getInstance();
		try {
			lDTO = alDAO.selectLogin(id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lDTO;
	}

	public LoginDTO getlDTO() {
		return lDTO;
	}
}
