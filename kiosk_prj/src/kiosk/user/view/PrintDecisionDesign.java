package kiosk.user.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import kiosk.user.event.PrintDecisionEvent;

public class PrintDecisionDesign extends JFrame {

	private JLabel jlblOrderNum;
	private JButton jbtnNp, jbtnYp;

	public PrintDecisionDesign() {
		super("영수증 출력 여부 선택");

		jlblOrderNum = new JLabel();
		jlblOrderNum.setHorizontalAlignment(SwingConstants.CENTER);

		jbtnNp = new JButton("미출력");
		jbtnYp = new JButton("출력");

		jlblOrderNum.setBounds(100, 50, 600, 200);
		jbtnNp.setBounds(100, 600, 600, 200);
		jbtnYp.setBounds(100, 300, 600, 200);

		this.setLayout(null);
		this.setResizable(false);

		add(jlblOrderNum);
		add(jbtnNp);
		add(jbtnYp);

		PrintDecisionEvent ue = new PrintDecisionEvent(this);
		jbtnNp.addActionListener(ue);
		jbtnYp.addActionListener(ue);
		addWindowListener(ue);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.setSize(800, 1000);
		this.setVisible(true);
	}// PrintDecisionDesign

	public JLabel getJlblOrderNum() {
		return jlblOrderNum;
	}// getJlblOrderNum

	public JButton getJbtnNp() {
		return jbtnNp;
	}// getJbtnNp

	public JButton getJbtnYp() {
		return jbtnYp;
	}// getJbtnYp

	public static void main(String[] args) {
		new PrintDecisionDesign();
	}// main

}// class
