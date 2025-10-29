package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.TakeOutDecisionDAO;

public class TakeOutDecisionService {

	private TakeOutDecisionDAO todDAO;

	public TakeOutDecisionService() {
		todDAO = TakeOutDecisionDAO.getInstance();
	}// TakeOutDecisionService

	public int modifyOrderList(String takeout_flg) {
		int flag = 0;

		try {
			flag = todDAO.updateOrderList(takeout_flg);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// modifyOrderList

	public int removeOrderList() {
		int flag = 0;

		try {
			flag = todDAO.deleteOrderList();
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
			flag = todDAO.deleteOrderDetail();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// removeOrderDetail

}// class
