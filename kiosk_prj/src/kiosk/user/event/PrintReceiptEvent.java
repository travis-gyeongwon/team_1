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
import kiosk.user.view.EndDesign;
import kiosk.user.view.PrintReceiptDesign;
import kiosk.user.view.StartDesign;

public class PrintReceiptEvent extends WindowAdapter implements ActionListener {

	private PrintReceiptDesign ud;
	private PrintReceiptService us;

	public PrintReceiptEvent(PrintReceiptDesign ud) {
		this.ud = ud;
		us = new PrintReceiptService();

		ud.getJlblOrderNum().setText("주문번호 : "+searchOrderList());;
		addData();
	}// PrintReceiptEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
		new EndDesign();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnCheck()) {
			ud.dispose();
			new EndDesign();
		} // end if
	}// actionPerformed

	public String searchOrderList() {
		String order_num = us.searchOrderList();

		return order_num;
	}// searchOrderList

	public List<PrintReceiptDTO> searchOrderDetail() {
		List<PrintReceiptDTO> list = us.searchOrderDetail();

		return list;
	}// searchOrderDetail

	public void addData() {
		List<PrintReceiptDTO> list = us.searchOrderDetail();
		DefaultTableModel dtm = ud.getDtm();
		dtm.setRowCount(0);

		List<Object> rowData = new ArrayList<Object>();
		for (PrintReceiptDTO prDTO : list) {
			rowData.add(prDTO.getMenu_name());
			rowData.add(prDTO.getAmount());
			rowData.add(prDTO.getOrder_price());

			dtm.addRow(rowData.toArray());
			rowData.clear();
		} // end for
	}// addData

}// class
