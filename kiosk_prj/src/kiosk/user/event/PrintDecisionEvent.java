package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kiosk.user.service.PrintReceiptService;
import kiosk.user.view.EndDesign;
import kiosk.user.view.PrintDecisionDesign;
import kiosk.user.view.PrintReceiptDesign;
import kiosk.user.view.StartDesign;

public class PrintDecisionEvent extends WindowAdapter implements ActionListener {

	private PrintDecisionDesign ud;
	private PrintReceiptService us;

	public PrintDecisionEvent(PrintDecisionDesign ud) {
		this.ud = ud;
		us = new PrintReceiptService();

		ud.getJlblOrderNum().setText("주문번호 : " + searchOrderList());
	}// PrintDecisionEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
		new StartDesign();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnNp()) {
			ud.dispose();
			new EndDesign();
		} // end if

		if (e.getSource() == ud.getJbtnYp()) {
			ud.dispose();
			new PrintReceiptDesign();
		} // end if
	}// actionPerformed

	public String searchOrderList() {
		String order_num = us.searchOrderList();

		return order_num;
	}// searchOrderList

}// class
