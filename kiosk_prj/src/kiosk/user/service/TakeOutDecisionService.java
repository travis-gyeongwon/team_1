package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.TakeOutDecisionDAO;

public class TakeOutDecisionService {

	public TakeOutDecisionService() {
	}// TakeOutDecisionService

	public int modifyOrderList(String takeout_flg) {
		int flag = 0;

		try {
			TakeOutDecisionDAO todDAO = TakeOutDecisionDAO.getInstance();
			flag = todDAO.updateOrderList(takeout_flg);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// modifyOrderList

}// class
