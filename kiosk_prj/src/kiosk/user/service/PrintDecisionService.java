package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;

import kiosk.user.dao.PrintDecisionDAO;
import kiosk.user.dto.MemberDTO;

public class PrintDecisionService {

	private PrintDecisionDAO pdDAO;

	public PrintDecisionService() {
		pdDAO = PrintDecisionDAO.getInstance();
	}// PrintDecisionService

	public int searchOrderDetail() {
		int total_amount = 0;

		try {
			total_amount = pdDAO.selectOrderDetail();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return total_amount;
	}// searchOrderDetail

	public String searchOrderList() {
		String phone = "";

		try {
			phone = pdDAO.selectOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return phone;
	}// searchOrderList

	public MemberDTO searchMember(String phone) {
		MemberDTO mDTO = null;

		try {
			mDTO = pdDAO.selectMember(phone);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return mDTO;
	}// searchMember

	public void modifyMember(String phone, int stamp, int coupon) {
		int flag = 0;

		try {
			flag = pdDAO.updateMember(phone, stamp, coupon);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch
	}// modifyMember

	public String searchOrderNum() {
		String order_num = "";

		try {
			order_num = pdDAO.selectOrderNum();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return order_num;
	}// searchOrderNum

}// class
