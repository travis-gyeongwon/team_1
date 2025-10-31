package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.StampInfoDAO;

public class StampInfoService {

	private StampInfoDAO siDAO;

	public StampInfoService() {
		siDAO = StampInfoDAO.getInstance();
	}// StampInfoService

	public int searchOrderDetail() {
		int total_amount = 0;

		try {
			total_amount = siDAO.selectOrderDetail();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return total_amount;
	}// searchOrderDetail

	public int modifyOrderList(String phone) {
		int flag = 0;

		try {

			flag = siDAO.updateOrderList(phone);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// modifyOrderList

}// class
