package kiosk.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kiosk.user.dao.PrintReceiptDAO;
import kiosk.user.dto.PrintReceiptDTO;

public class PrintReceiptService {

	public PrintReceiptService() {
	}// PrintReceiptService

	public List<PrintReceiptDTO> searchOrderDetail(String order_num) {
		List<PrintReceiptDTO> list = null;

		try {
			PrintReceiptDAO prDAO = PrintReceiptDAO.getInstance();
			list = prDAO.selectOrderDetail(order_num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		return list;
	}// searchOrderDetail

}// class
