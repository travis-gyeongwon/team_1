package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.JoinDecisionDAO;

public class JoinDecisionService {

	public JoinDecisionService() {
	}// JoinDecisionService

	public int addMember(String phone) {
		int flag = 0;

		try {
			JoinDecisionDAO jdDAO = JoinDecisionDAO.getInstance();
			flag = jdDAO.insertMember(phone);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// addMember

}// class
