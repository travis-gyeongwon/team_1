package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kiosk.user.dto.MemberDTO;
import kiosk.user.service.PrintDecisionService;
import kiosk.user.view.EndDesign;
import kiosk.user.view.PrintDecisionDesign;
import kiosk.user.view.PrintReceiptDesign;

public class PrintDecisionEvent extends WindowAdapter implements ActionListener {

	private PrintDecisionDesign ud;
	private PrintDecisionService us;
	private String phone;
	private int temp_stamp;

	public PrintDecisionEvent(PrintDecisionDesign ud) {
		this.ud = ud;
		us = new PrintDecisionService();

		phone = us.searchOrderList();
		temp_stamp = us.searchOrderDetail();

		modifyMember();

		ud.getJlblOrderNum().setText("주문번호 : " + searchOrderNum());
	}// PrintDecisionEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
		new EndDesign();
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

	public String searchOrderNum() {
		String order_num = us.searchOrderNum();

		return order_num;
	}// searchOrderNum

	public void modifyMember() {
		MemberDTO mDTO = us.searchMember(phone);

		if (mDTO != null) {
			int total_stamp = mDTO.getStamp() + temp_stamp;
			int total_coupon = mDTO.getCoupon();

			if (total_stamp > 9) {
				int re_stamp = total_stamp % 10;
				int temp_coupon = total_stamp / 10;

				total_coupon += temp_coupon;

				us.modifyMember(phone, re_stamp, total_coupon);
			} else {
				us.modifyMember(phone, total_stamp, total_coupon);
			} // end else

		} // end if
	}// modifyMember

}// class
