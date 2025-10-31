package kiosk.user.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.BorderFactory;

import kiosk.user.event.PrintReceiptEvent;

public class PrintReceiptDesign extends JFrame {

	private JLabel jlblOrderNum, jlblTotalPrice, jlblTel, jlblStamp, jlblCoupon, jlblTakeOut, jlblCheckOut, jlblTime;
	private JTextField jtpTotalPrice, jtpTel, jtpStamp, jtpCoupon, jtpTakeOut, jtpCheckOut, jtpTime;
	private JTable jtblOrderList;
	private DefaultTableModel dtm;
	private JScrollPane jspOrderList;
	private JButton jbtnCheck;

	private static final Font FONT_LABEL = new Font("Malgun Gothic", Font.BOLD, 20);
	private static final Font FONT_FIELD = new Font("Malgun Gothic", Font.PLAIN, 18);
	private static final Font FONT_BUTTON = new Font("Malgun Gothic", Font.BOLD, 22);

	public PrintReceiptDesign() {
		super("영수증 출력");

		this.getContentPane().setBackground(Color.WHITE);

		jlblOrderNum = new JLabel();
		jlblOrderNum.setHorizontalAlignment(SwingConstants.CENTER);
		jlblOrderNum.setFont(FONT_LABEL);
		jlblOrderNum.setForeground(Color.BLACK);

		jlblTotalPrice = new JLabel("총 금액 : ");
		jlblTotalPrice.setFont(FONT_LABEL);
		jtpTotalPrice = createStyledJtf();

		jlblTel = new JLabel("스탬프 적립 : ");
		jlblTel.setFont(FONT_LABEL);
		jtpTel = createStyledJtf();

		jlblStamp = new JLabel("보유 스탬프  : ");
		jlblStamp.setFont(FONT_LABEL);
		jtpStamp = createStyledJtf();

		jlblCoupon = new JLabel("보유 쿠폰  : ");
		jlblCoupon.setFont(FONT_LABEL);
		jtpCoupon = createStyledJtf();

		jlblTakeOut = new JLabel("포장 여부  : ");
		jlblTakeOut.setFont(FONT_LABEL);
		jtpTakeOut = createStyledJtf();

		jlblCheckOut = new JLabel("결제 방식  : ");
		jlblCheckOut.setFont(FONT_LABEL);
		jtpCheckOut = createStyledJtf();

		jlblTime = new JLabel("결제 시간  : ");
		jlblTime.setFont(FONT_LABEL);
		jtpTime = createStyledJtf();

		String[] colNames = { "상품명", "수량", "금액" };
		dtm = new DefaultTableModel(colNames, 0);
		jtblOrderList = new JTable(dtm);

		jtblOrderList.getColumnModel().getColumn(0).setPreferredWidth(300);
		jtblOrderList.getColumnModel().getColumn(1).setPreferredWidth(100);
		jtblOrderList.getColumnModel().getColumn(2).setPreferredWidth(100);
		jspOrderList = new JScrollPane(jtblOrderList);

		jtblOrderList.setFont(FONT_FIELD);
		jtblOrderList.setForeground(Color.BLACK);
		jtblOrderList.setBackground(Color.WHITE);
		jtblOrderList.setGridColor(Color.BLACK);
		jtblOrderList.setRowHeight(25);
		jtblOrderList.setShowVerticalLines(true);
		jtblOrderList.setShowHorizontalLines(true);

		JTableHeader tableHeader = jtblOrderList.getTableHeader();
		tableHeader.setFont(FONT_LABEL);
		tableHeader.setBackground(Color.BLACK);
		tableHeader.setForeground(Color.WHITE);
		tableHeader.setReorderingAllowed(false);
		tableHeader.setResizingAllowed(false);

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setBackground(Color.WHITE);
				c.setForeground(Color.BLACK);

				if (isSelected) {
					c.setBackground(new Color(220, 220, 220));
					c.setForeground(Color.BLACK);
				}
				return c;
			}
		};

		for (int i = 0; i < jtblOrderList.getColumnCount(); i++) {
			jtblOrderList.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}

		jspOrderList = new JScrollPane(jtblOrderList);

		jspOrderList.setBackground(Color.WHITE);
		jspOrderList.getViewport().setBackground(Color.WHITE);
		jspOrderList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		jbtnCheck = new JButton("확인");
		jbtnCheck.setBackground(Color.BLACK);
		jbtnCheck.setForeground(Color.WHITE);
		jbtnCheck.setFont(FONT_BUTTON);
		jbtnCheck.setFocusPainted(false);
		jbtnCheck.setBorderPainted(false);

		jlblOrderNum.setBounds(100, 50, 500, 30);
		jspOrderList.setBounds(100, 100, 500, 350);
		jlblTel.setBounds(100, 470, 150, 30);
		jtpTel.setBounds(250, 470, 350, 30);
		jlblStamp.setBounds(100, 510, 150, 30);
		jtpStamp.setBounds(250, 510, 350, 30);
		jlblCoupon.setBounds(100, 550, 150, 30);
		jtpCoupon.setBounds(250, 550, 350, 30);
		jlblTakeOut.setBounds(100, 590, 150, 30);
		jtpTakeOut.setBounds(250, 590, 350, 30);
		jlblCheckOut.setBounds(100, 630, 150, 30);
		jtpCheckOut.setBounds(250, 630, 350, 30);
		jlblTime.setBounds(100, 670, 150, 30);
		jtpTime.setBounds(250, 670, 350, 30);
		jlblTotalPrice.setBounds(100, 710, 150, 30);
		jtpTotalPrice.setBounds(250, 710, 350, 30);
		jbtnCheck.setBounds(100, 790, 500, 50);

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

		this.setSize(700, 1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}// PrintReceiptDesign

	private JTextField createStyledJtf() {
		JTextField jtf = new JTextField();
		jtf.setEditable(false);

		jtf.setBackground(Color.WHITE);
		jtf.setForeground(Color.BLACK);
		jtf.setFont(FONT_FIELD);
		jtf.setBorder(BorderFactory.createEmptyBorder());
		return jtf;
	}// createStyledJtf

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

}// class
