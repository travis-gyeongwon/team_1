package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import kiosk.user.dto.PrintReceiptDTO;
import kiosk.user.service.PrintReceiptService;
import kiosk.user.view.PrintReceiptDesign;

public class PrintReceiptEvent extends WindowAdapter implements ActionListener {

	private PrintReceiptDesign ud;
	private PrintReceiptService us;

	public PrintReceiptEvent(PrintReceiptDesign ud) {
		this.ud = ud;
		us = new PrintReceiptService();

		addData("2510160001");
	}// PrintReceiptEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnCheck()) {
//			ud.dispose();
//			new EndDesign();
		} // end if
	}// actionPerformed

	public List<PrintReceiptDTO> searchOrderDetail(String order_num) {
		List<PrintReceiptDTO> list = null;
		list = us.searchOrderDetail(order_num);

		return list;
	}// searchOrderDetail

	public void addData(String order_num) {
		List<PrintReceiptDTO> list = us.searchOrderDetail(order_num);
		DefaultTableModel dtm = ud.getDtm();
		dtm.setRowCount(0);

		List<Object> rowData = new ArrayList<Object>();
		String total_name = "";
		for (PrintReceiptDTO prDTO : list) {
			rowData.add(prDTO.getMenu_name());
			rowData.add(prDTO.getAmount());
			rowData.add(prDTO.getOrder_price());

			dtm.addRow(rowData.toArray());
			rowData.clear();
		} // end for
	}// addData

}// class
