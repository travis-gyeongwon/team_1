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

	private OrderDesign od;
	private TakeOutDecisionDesign ud;
	private TakeOutDecisionService us;

	public TakeOutDecisionEvent(OrderDesign od, TakeOutDecisionDesign ud) {
		this.od = od;
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
			modifyOrderList("N");
		} // end if

		if (e.getSource() == ud.getJbtnToGo()) {
			modifyOrderList("Y");
		} // end if
	}// actionPerformed

	public void modifyOrderList(String takeout_flg) {
		int flag = us.modifyOrderList(takeout_flg);
		
		switch (flag) {
		case 0:
			JOptionPane.showMessageDialog(ud, "문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
			break;
		case 1:
			ud.dispose();
			new SaveStampDesign(od);
			break;
		}// end switch
	}// modifyOrderList

}// class
