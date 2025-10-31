package kiosk.user.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import kiosk.user.event.PrintDecisionEvent;

public class PrintDecisionDesign extends JFrame {

	private JLabel jlblOrderNum;
	private JButton jbtnNp, jbtnYp;

	private static final Font FONT_HEADER = new Font("Malgun Gothic", Font.BOLD, 30);
	private static final Font FONT_BUTTON = new Font("Malgun Gothic", Font.BOLD, 40);

	public PrintDecisionDesign() {
		super("영수증 출력 여부 선택");

		jlblOrderNum = new JLabel();
		jlblOrderNum.setHorizontalAlignment(SwingConstants.CENTER);
		jlblOrderNum.setFont(FONT_HEADER);
		jlblOrderNum.setForeground(Color.BLACK);

		jbtnNp = new JButton("미출력");
		jbtnNp.setBackground(Color.LIGHT_GRAY);
		jbtnNp.setForeground(Color.BLACK);
		jbtnNp.setFont(FONT_BUTTON);
		jbtnNp.setFocusPainted(false);
		jbtnNp.setBorderPainted(false);

		jbtnYp = new JButton("출력");
		jbtnYp.setBackground(Color.LIGHT_GRAY);
		jbtnYp.setForeground(Color.BLACK);
		jbtnYp.setFont(FONT_BUTTON);
		jbtnYp.setFocusPainted(false);
		jbtnYp.setBorderPainted(false);

		jlblOrderNum.setBounds(100, 50, 500, 200);
		jbtnNp.setBounds(100, 600, 500, 200);
		jbtnYp.setBounds(100, 300, 500, 200);

		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.WHITE);

		add(jlblOrderNum);
		add(jbtnNp);
		add(jbtnYp);

		PrintDecisionEvent ue = new PrintDecisionEvent(this);
		jbtnNp.addActionListener(ue);
		jbtnYp.addActionListener(ue);
		addWindowListener(ue);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.setSize(700, 1000);
		this.setLocationRelativeTo(null);
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

}// class
