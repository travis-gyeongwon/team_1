package kiosk.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kiosk.admin.dao.InvenManageDAO;
import kiosk.admin.dto.InvenManageDTO;

public class InvenManageService {
	public List<InvenManageDTO> searchAllInventory() {
		List<InvenManageDTO> listImDTO = new ArrayList<InvenManageDTO>();
		
		try {
			InvenManageDAO imDAO = InvenManageDAO.getInstance();
			listImDTO = imDAO.selectAllInven();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listImDTO;
	}// searchAllInventory
	
	public int modifyAllInventory() {
		int flag = 0;
		
		InvenManageDAO imDAO = InvenManageDAO.getInstance();
		try {
			flag = imDAO.updateAllInventory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}// modifyAllInventory
	
	public int modifyOneInventory(InvenManageDTO imDTO) {
		int flag = 0;
		
		InvenManageDAO imDAO = InvenManageDAO.getInstance();
		try {
			flag = imDAO.updateOneInventory(imDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// end catch
		
		return flag;
	}// modifyAllInventory
}// InvenManageService
