package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kiosk.user.dao.PrintReceiptDAO;
import kiosk.user.dto.MemberDTO;
import kiosk.user.dto.OrderDetailDTO;
import kiosk.user.dto.OrderListDTO;

public class PrintReceiptService {

	private PrintReceiptDAO prDAO;

	public PrintReceiptService() {
		prDAO = PrintReceiptDAO.getInstance();
	}// PrintReceiptService

	public OrderListDTO searchOrderList() {
		OrderListDTO olDTO = null;

		try {
			olDTO = prDAO.selectOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return olDTO;
	}// searchOrderList

	public String searchPhone() {
		String phone = "";

		try {
			phone = prDAO.selectPhone();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return phone;
	}// searchPhone

	public String searchOrderNum() {
		String order_num = "";

		try {
			order_num = prDAO.selectOrderNum();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return order_num;
	}// searchOrderNum

	public MemberDTO searchMember(String phone) {
		MemberDTO mDTO = null;

		try {
			mDTO = prDAO.selectMember(phone);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return mDTO;
	}// searchMember

	public List<OrderDetailDTO> searchOrderDetail() {
		List<OrderDetailDTO> list = null;

		try {
			list = prDAO.selectOrderDetail();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return list;
	}// searchOrderDetail

}// class
