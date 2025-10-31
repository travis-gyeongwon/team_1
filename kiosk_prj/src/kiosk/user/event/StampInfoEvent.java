package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kiosk.user.service.StampInfoService;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.OrderPayDesign;
import kiosk.user.view.SaveStampDesign;
import kiosk.user.view.StampInfoDesign;

public class StampInfoEvent extends WindowAdapter implements ActionListener {

	private OrderDesign od;
	private SaveStampDesign ssd;
	private StampInfoDesign ud;
	private StampInfoService us;
	private String phone;

	public StampInfoEvent(OrderDesign od, StampInfoDesign ud, SaveStampDesign ssd) {
		this.od = od;
		this.ssd = ssd;
		this.ud = ud;
		us = new StampInfoService();

		ud.getJtpStampInfo().setText("스탬프 " + us.searchOrderDetail() + "개가 적립되었습니다.");
		phone = ud.getSsd().getJtfTel().getText();
	}// StampInfoEvent

	@Override
	public void windowClosing(WindowEvent e) {
		us.modifyOrderList(phone);

		ud.dispose();
		ssd.dispose();
		new OrderPayDesign(od);
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnCheck()) {
			us.modifyOrderList(phone);

			ud.dispose();
			ssd.dispose();
			new OrderPayDesign(od);
		} // end if
	}// actionPerformed

}// class
