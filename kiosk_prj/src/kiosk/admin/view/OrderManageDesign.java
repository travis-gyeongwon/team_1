package kiosk.admin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kiosk.admin.event.OrderManageEvent;

public class OrderManageDesign extends JDialog {

	private JTable jtOrderTable;
	private DefaultTableModel dtmOrderTable;
	private JButton jbtnRenew;
	private JLabel jlblRenewTime;

	public OrderManageDesign(AdminMainDesign amd) {
		super(amd,"주문관리창",true);

		JLabel jlblInstruction = new JLabel("관리할 주문을 선택해주세요");
		String header[] = { "주문번호", "주문시간", "대표상품", "금액", "주문상태", "픽업완료" };
		dtmOrderTable = new DefaultTableModel(header, 0);
		jtOrderTable = new JTable(dtmOrderTable);
		JScrollPane jsp = new JScrollPane(jtOrderTable);
		jbtnRenew = new JButton("갱신");
		jlblRenewTime = new JLabel();

		setLayout(null);

		jlblInstruction.setFont(new Font("맑은 고딕", Font.BOLD, 30));

		jlblInstruction.setBounds(110, 30, 500, 35);
		jsp.setBounds(70, 80, 660, 300);
		jbtnRenew.setBounds(70, 400, 80, 40);
		jlblRenewTime.setBounds(170, 400, 300, 40);

		add(jlblInstruction);
		add(jsp);
		add(jbtnRenew);
		add(jlblRenewTime);

		OrderManageEvent ome = OrderManageEvent.getInstance(this);
		jbtnRenew.addActionListener(ome);
		jtOrderTable.addMouseListener(ome);
		addWindowListener(ome);
//		ome.searchAllMakingOrder();

		setSize(800,550);
		setLocationRelativeTo(amd);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	}// OrderManageDesign

	public JTable getJtOrderTable() {
		return jtOrderTable;
	}

	public DefaultTableModel getDtmOrderTable() {
		return dtmOrderTable;
	}

	public JButton getJbtnRenew() {
		return jbtnRenew;
	}

	public JLabel getJlblRenewTime() {
		return jlblRenewTime;
	}

}// class
