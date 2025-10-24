package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.service.TakeOutDecisionService;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.SaveStampDesign;
import kiosk.user.view.TakeOutDecisionDesign;

public class TakeOutDecisionEvent extends WindowAdapter implements ActionListener {

	private OrderDesign ut;
	private TakeOutDecisionDesign ud;
	private TakeOutDecisionService us;

	public TakeOutDecisionEvent(OrderDesign ut, TakeOutDecisionDesign ud) {
		this.ut = ut;
		this.ud = ud;
		us = new TakeOutDecisionService();
	}// TakeOutDecisionEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnForHere()) {
			modifyOrderList("2510160001", "N");
		} // end if

		if (e.getSource() == ud.getJbtnToGo()) {
			modifyOrderList("2510160001", "Y");
		} // end if
	}// actionPerformed

	public void modifyOrderList(String order_num, String takeout_flg) {
		int flag = us.modifyOrderList(order_num, takeout_flg);
		
		switch (flag) {
		case 0:
			JOptionPane.showMessageDialog(ud, "문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
			break;
		case 1:
			ud.dispose();
			new SaveStampDesign(ut);
			break;
		}// end switch
	}// modifyOrderList

}// class
