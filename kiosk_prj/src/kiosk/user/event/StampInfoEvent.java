package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

import kiosk.user.dto.StampInfoDTO;
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
	private int temp_stamp, pre_stamp, total_stamp;

	public StampInfoEvent(OrderDesign od, StampInfoDesign ud, SaveStampDesign ssd) {
		this.od = od;
		this.ssd=ssd;
		this.ud = ud;
		us = new StampInfoService();

		temp_stamp = stampCalculator(searchOrderDetail());
		ud.getJtpStampInfo().setText("스탬프 " + temp_stamp + "개가 적립되었습니다.");

		phone = ud.getSsd().getJtfTel().getText();
		pre_stamp = searchMember(phone);

		total_stamp = temp_stamp + pre_stamp;
	}// StampInfoEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ud.getJbtnCheck()) {
			modifyMember(phone, total_stamp);
		} // end if
	}// actionPerformed

	public int stampCalculator(List<StampInfoDTO> list) {
		int result = 0;

		for (StampInfoDTO siDTO : list) {
			result += siDTO.getAmount();
		} // end for

		return result;
	}// stampCalculator

	public List<StampInfoDTO> searchOrderDetail() {
		List<StampInfoDTO> list = null;
		list = us.searchOrderDetail();

		return list;
	}// searchOrderDetail

	public int searchMember(String phone) {
		int result = 0;
		result = us.searchMember(phone);

		return result;
	}// searchMember

	public void modifyMember(String phone, int total_stamp) {
		int flag = us.modifyMember(phone, total_stamp);

		switch (flag) {
		case 0:
			JOptionPane.showMessageDialog(ud, "문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
			break;
		case 1:
			ud.dispose();
			ssd.dispose();
			new OrderPayDesign(od);
			break;
		}// end switch
	}// modifyMember

}// class
