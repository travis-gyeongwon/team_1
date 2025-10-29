package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
	private boolean isProceeding = false;

	public SaveStampEvent(OrderDesign od, SaveStampDesign ud) {
		this.od = od;
		this.ud = ud;
		us = new SaveStampService();
	}// SaveStampEvent

	@Override
	public void windowClosing(WindowEvent e) {
		if (!isProceeding) {
			us.removeOrderDetail();
			us.removeOrderList();
		}// end if

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
			}
		} // end if

		if (e.getSource() == ud.getJbtnNs()) {
			isProceeding = true;
			ud.dispose();
			new OrderPayDesign(od);
		} // end if

		if (e.getSource() == ud.getJbtnYs()) {
			if (ud.getJtfTel().getText().length() != 11) {
				JOptionPane.showMessageDialog(ud, "휴대폰 번호 11자리를 입력해주세요.");
				return;
			} // end if

			if (isValidTel()) {
				searchMember(input);
			} else {
				JOptionPane.showMessageDialog(ud, "유효한 휴대폰 번호를 입력해주세요.");
			} // end if
		} // end if
	}// actionPerformed

	public boolean isValidTel() {
		boolean flag = false;

		String code = ud.getJtfTel().getText().substring(0, 3);
		List<String> validList = new ArrayList<String>();

		validList.add("010");
		validList.add("011");
		validList.add("016");
		validList.add("017");
		validList.add("018");
		validList.add("019");

		if (validList.indexOf(code) != -1) {
			flag = true;
		} // end if

		return flag;
	}// isValidTel

	public void searchMember(String phone) {
		int flag = us.searchMember(phone);

		switch (flag) {
		case 0:
			isProceeding = true;
			new JoinDecisionDesign(ud);
			break;
		case 1:
			isProceeding = true;
			new StampInfoDesign(ud);
			break;
		}// end switch
	}// searchMember

}// class
