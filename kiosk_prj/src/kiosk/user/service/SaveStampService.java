package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.SaveStampDAO;

public class SaveStampService {

	private SaveStampDAO ssDAO;

	public SaveStampService() {
		ssDAO = SaveStampDAO.getInstance();
	}// SaveStampService

	public int searchMember(String phone) {
		int flag = 0;

		try {
			flag = ssDAO.selectMember(phone);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// searchMember

	public int removeOrderList() {
		int flag = 0;

		try {
			flag = ssDAO.deleteOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// removeOrderList

	public int removeOrderDetail() {
		int flag = 0;

		try {
			flag = ssDAO.deleteOrderDetail();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// removeOrderDetail

}// class
