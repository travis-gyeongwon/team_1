package kiosk.user.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import kiosk.user.event.PrintReceiptEvent;

public class PrintReceiptDesign extends JFrame {

	private JLabel jlblOrderNum, jlblTotalPrice, jlblTel, jlblStamp, jlblCoupon, jlblTakeOut, jlblCheckOut, jlblTime;
	private JTextField jtpTotalPrice, jtpTel, jtpStamp, jtpCoupon, jtpTakeOut, jtpCheckOut, jtpTime;
	private JTable jtblOrderList;
	private DefaultTableModel dtm;
	private JScrollPane jspOrderList;
	private JButton jbtnCheck;

	public PrintReceiptDesign() {
		super("영수증 출력");

		jlblOrderNum = new JLabel();
		jlblOrderNum.setHorizontalAlignment(SwingConstants.CENTER);

		jlblTotalPrice = new JLabel("총 금액 : ");
		jtpTotalPrice = new JTextField();
		jtpTotalPrice.setEditable(false);

		jlblTel = new JLabel("스탬프 적립 : ");
		jtpTel = new JTextField();
		jtpTel.setEditable(false);

		jlblStamp = new JLabel("보유 스탬프  : ");
		jtpStamp = new JTextField();
		jtpStamp.setEditable(false);

		jlblCoupon = new JLabel("보유 쿠폰  : ");
		jtpCoupon = new JTextField();
		jtpCoupon.setEditable(false);

		jlblTakeOut = new JLabel("포장 여부  : ");
		jtpTakeOut = new JTextField();
		jtpTakeOut.setEditable(false);

		jlblCheckOut = new JLabel("결제 방식  : ");
		jtpCheckOut = new JTextField();
		jtpCheckOut.setEditable(false);

		jlblTime = new JLabel("결제 시간  : ");
		jtpTime = new JTextField();
		jtpTime.setEditable(false);

		String[] colNames = { "상품명", "수량", "금액" };

		dtm = new DefaultTableModel(colNames, 0);

		jtblOrderList = new JTable(dtm);
		jspOrderList = new JScrollPane(jtblOrderList);
		jbtnCheck = new JButton("확인");

		jlblOrderNum.setBounds(100, 50, 600, 30);
		jspOrderList.setBounds(100, 100, 600, 350);
		jlblTel.setBounds(150, 470, 100, 30);
		jtpTel.setBounds(250, 470, 350, 30);
		jlblStamp.setBounds(150, 510, 100, 30);
		jtpStamp.setBounds(250, 510, 350, 30);
		jlblCoupon.setBounds(150, 550, 100, 30);
		jtpCoupon.setBounds(250, 550, 350, 30);
		jlblTakeOut.setBounds(150, 590, 100, 30);
		jtpTakeOut.setBounds(250, 590, 350, 30);

		jlblCheckOut.setBounds(150, 630, 100, 30);
		jtpCheckOut.setBounds(250, 630, 350, 30);
		jlblTime.setBounds(150, 670, 100, 30);
		jtpTime.setBounds(250, 670, 350, 30);

		jlblTotalPrice.setBounds(150, 710, 100, 30);
		jtpTotalPrice.setBounds(250, 710, 350, 30);
		jbtnCheck.setBounds(100, 790, 600, 50);

		this.setLayout(null);
		this.setResizable(false);

		PrintReceiptEvent ue = new PrintReceiptEvent(this);
		jbtnCheck.addActionListener(ue);
		addWindowListener(ue);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		add(jlblOrderNum);
		add(jspOrderList);
		add(jlblCoupon);
		add(jtpCoupon);
		add(jlblStamp);
		add(jtpStamp);
		add(jlblTel);
		add(jtpTel);
		add(jlblTakeOut);
		add(jtpTakeOut);
		add(jlblCheckOut);
		add(jtpCheckOut);
		add(jlblTime);
		add(jtpTime);
		add(jlblTotalPrice);
		add(jtpTotalPrice);
		add(jbtnCheck);

		this.setSize(800, 1000);
		this.setVisible(true);
	}// PrintReceiptDesign

	public JLabel getJlblOrderNum() {
		return jlblOrderNum;
	}// getJlblOrderNum

	public DefaultTableModel getDtm() {
		return dtm;
	}// getDtm

	public JTable getJtblOrderList() {
		return jtblOrderList;
	}// getJtblOrderList

	public JButton getJbtnCheck() {
		return jbtnCheck;
	}// getJbtnCheck

	public JTextField getJtpTel() {
		return jtpTel;
	}// getJtpTel

	public JTextField getJtpStamp() {
		return jtpStamp;
	}// getJtpStamp

	public JTextField getJtpCoupon() {
		return jtpCoupon;
	}// getJtpCoupon

	public JTextField getJtpTakeOut() {
		return jtpTakeOut;
	}// getJtpTakeOut

	public JTextField getJtpTotalPrice() {
		return jtpTotalPrice;
	}// getJtpTotalPrice

	public JTextField getJtpCheckOut() {
		return jtpCheckOut;
	}// getJtpCheckOut

	public JTextField getJtpTime() {
		return jtpTime;
	}// getJtpTime

	public JLabel getJlblTel() {
		return jlblTel;
	}// getJlblTel

	public JLabel getJlblStamp() {
		return jlblStamp;
	}// getJlblStamp

	public JLabel getJlblCoupon() {
		return jlblCoupon;
	}// getJlblCoupon

	public static void main(String[] args) {
		new PrintReceiptDesign();
	}// main

}// class
