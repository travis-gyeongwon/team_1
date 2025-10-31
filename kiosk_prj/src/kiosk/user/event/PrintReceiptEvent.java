package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import kiosk.user.dto.MemberDTO;
import kiosk.user.dto.OrderDetailDTO;
import kiosk.user.dto.OrderListDTO;
import kiosk.user.service.PrintReceiptService;
import kiosk.user.view.EndDesign;
import kiosk.user.view.PrintReceiptDesign;

public class PrintReceiptEvent extends WindowAdapter implements ActionListener {

	private PrintReceiptDesign ud;
	private PrintReceiptService us;
	private List<OrderDetailDTO> list;

	public PrintReceiptEvent(PrintReceiptDesign ud) {
		this.ud = ud;
		us = new PrintReceiptService();

		ud.getJlblOrderNum().setText("주문번호 : " + searchOrderNum());

		searchOrderList();
		searchMember();

		list = us.searchOrderDetail();

		addData();
		addTotalPrice();
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

	public String searchOrderNum() {
		String order_num = us.searchOrderNum();

		return order_num;
	}// searchOrderNum

	public String searchPhone() {
		String phone = us.searchPhone();

		return phone;
	}// searchPhone

	public void searchOrderList() {
		OrderListDTO olDTO = us.searchOrderList();

		if ("N".equals(olDTO.getTakeout_flg())) {
			ud.getJtpTakeOut().setText("홀");
		} else {
			ud.getJtpTakeOut().setText("포장");
		} // end else

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ud.getJtpTime().setText(sdf.format(olDTO.getOrder_time()));

		if (olDTO.getCheckout_code() == 1) {
			ud.getJtpCheckOut().setText("카드");
		} else {
			ud.getJtpCheckOut().setText("페이");
		} // end else
	}// searchOrderList

	public void searchMember() {
		MemberDTO mDTO = us.searchMember(searchPhone());

		if (mDTO == null) {
			setInvisibleMember();
		} else {
			ud.getJtpTel().setText(mDTO.getPhone());
			ud.getJtpStamp().setText(String.valueOf(mDTO.getStamp()) + "개");
			ud.getJtpCoupon().setText(String.valueOf(mDTO.getCoupon()) + "개");
		} // end else
	}// searchMember

	public void setInvisibleMember() {
		ud.getJlblTel().setVisible(false);
		ud.getJtpTel().setVisible(false);
		ud.getJlblStamp().setVisible(false);
		ud.getJtpStamp().setVisible(false);
		ud.getJlblCoupon().setVisible(false);
		ud.getJtpCoupon().setVisible(false);
	}// setInvisibleMember

	public void addData() {
		DefaultTableModel dtm = ud.getDtm();
		dtm.setRowCount(0);

		List<Object> rowData = new ArrayList<Object>();
		for (OrderDetailDTO odDTO : list) {
			rowData.add(odDTO.getMenu_name());
			rowData.add(odDTO.getAmount());
			rowData.add(odDTO.getOrder_price());

			dtm.addRow(rowData.toArray());
			rowData.clear();
		} // end for
	}// addData

	public void addTotalPrice() {
		int total_price = 0;

		for (OrderDetailDTO odDTO : list) {
			total_price += odDTO.getOrder_price();
		} // end for

		ud.getJtpTotalPrice().setText(String.valueOf(total_price)+"원");
	}// addTotalPrice

}// class
