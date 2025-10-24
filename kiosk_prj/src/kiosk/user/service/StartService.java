package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import kiosk.user.dao.StartDAO;

public class StartService {
	
	String bestMenu;
	
	public String showBestmenu() {
		
		//이미지 경로
		try {
			bestMenu = StartDAO.getInstance().selectBestMenu();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
		
		return bestMenu;
		
	}//showBestmenu

}//class
