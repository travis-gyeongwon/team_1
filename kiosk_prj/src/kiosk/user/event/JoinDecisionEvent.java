package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.service.JoinDecisionService;
import kiosk.user.view.JoinDecisionDesign;
import kiosk.user.view.StampInfoDesign;

public class JoinDecisionEvent extends WindowAdapter implements ActionListener {

	private JoinDecisionDesign ud;
	private JoinDecisionService us;

	public JoinDecisionEvent(JoinDecisionDesign ud) {
		this.ud = ud;
		us = new JoinDecisionService();
	}// JoinDecisionEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnNj()) {
			ud.dispose();
		} // end if

		if (e.getSource() == ud.getJbtnYj()) {
			ud.dispose();
			addMember(ud.getSsd().getJtfTel().getText());
		} // end if
	}// actionPerformed

	public void addMember(String phone) {
		int flag = us.addMember(phone);

		switch (flag) {
		case 0:
			JOptionPane.showMessageDialog(ud, "문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
			break;
		case 1:
			ud.dispose();
			new StampInfoDesign(ud.getSsd());
			break;
		}// end switch
	}// addMember

}// class
