package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import kiosk.user.dao.StartDAO;

public class StartService {
	
	ImageIcon bestMenu;
	
	
	public boolean showStoreStatus() {
		
		boolean statusFlag=false;
		
		try {
			switch(StartDAO.getInstance().selectStoreStatus()) {
			
			case "Y": statusFlag=true; break; //영업중
			case "y": statusFlag=true; break; //영업중
			case "N": statusFlag=false; break; //영업종료
			case "n": statusFlag=false; break; //영업종료
				
			}//end switch
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
		return statusFlag;
		
	}//showStoreStatus
	
	public ImageIcon showBestmenu() {
		
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
