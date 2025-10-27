package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.service.SaveStampService;
import kiosk.user.view.JoinDecisionDesign;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.OrderPayDesign;
import kiosk.user.view.SaveStampDesign;
import kiosk.user.view.StampInfoDesign;

public class SaveStampEvent extends WindowAdapter implements ActionListener {

	private OrderDesign od;
	private SaveStampDesign ud;
	private SaveStampService us;

	public SaveStampEvent(OrderDesign od, SaveStampDesign ud) {
		this.od = od;
		this.ud = ud;
		us = new SaveStampService();
	}// SaveStampEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		int ind = ud.getKeyBtns().indexOf(e.getSource());
		String input = ud.getJtfTel().getText();

		if (ind != -1) {
			String key = ud.getKeyNums()[ind];

			if ("←".equals(key)) {
				if (!input.isEmpty()) {
					ud.getJtfTel().setText(input.substring(0, input.length() - 1));
				} // end if

				return;
			} // end if

			if ((input + key).length() < 12) {
				ud.getJtfTel().setText(input + key);
			} else {
				JOptionPane.showMessageDialog(ud, "전화번호는 최대 11자리까지 입력 가능합니다.");
			} // end else
		} // end if

		if (e.getSource() == ud.getJbtnNs()) {
			ud.dispose();
			new OrderPayDesign(od);
		} // end if

		if (e.getSource() == ud.getJbtnYs()) {
			searchMember(input);
		} // end if
	}// actionPerformed

	public void searchMember(String phone) {
		int flag = us.searchMember(phone);

		switch (flag) {
		case 0:
			new JoinDecisionDesign(ud);
			break;
		case 1:
			new StampInfoDesign(ud);
			break;
		}// end switch
	}// searchMember

}// class
