package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kiosk.user.dao.PrintReceiptDAO;
import kiosk.user.dto.OrderDetailDTO;

public class PrintReceiptService {

	private PrintReceiptDAO prDAO;

	public PrintReceiptService() {
		prDAO = PrintReceiptDAO.getInstance();
	}// PrintReceiptService

	public String searchOrderList() {
		String order_num = "";

		try {
			order_num = prDAO.selectOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return order_num;
	}// searchOrderList

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
