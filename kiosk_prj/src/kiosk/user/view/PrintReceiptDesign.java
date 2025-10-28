package kiosk.user.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import kiosk.user.event.PrintReceiptEvent;

public class PrintReceiptDesign extends JFrame {

	private JLabel jlblOrderNum;
	private JTable jtblOrderList;
	private DefaultTableModel dtm;
	private JScrollPane jspOrderList;
	private JButton jbtnCheck;

	public PrintReceiptDesign() {
		super("영수증 출력");

		jlblOrderNum = new JLabel("주문번호 : 2510160001");
		jlblOrderNum.setHorizontalAlignment(SwingConstants.CENTER);

		String[] colNames = { "상품명", "수량", "금액" };

		dtm = new DefaultTableModel(colNames, 0);

		jtblOrderList = new JTable(dtm);
		jspOrderList = new JScrollPane(jtblOrderList);
		jbtnCheck = new JButton("확인");

		jlblOrderNum.setBounds(100, 50, 600, 200);
		jspOrderList.setBounds(100, 300, 600, 550);
		jbtnCheck.setBounds(100, 900, 600, 50);

		this.setLayout(null);
		this.setResizable(false);

		PrintReceiptEvent ue = new PrintReceiptEvent(this);
		jbtnCheck.addActionListener(ue);

		add(jlblOrderNum);
		add(jspOrderList);
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

}// class
