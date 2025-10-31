package kiosk.user.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import kiosk.user.event.SaveStampEvent;

public class SaveStampDesign extends JDialog {

	private OrderDesign od;
	private JTextField jtfTel;
	private String[] keyNums;
	private List<JButton> keyBtns;
	private JButton jbtnNs, jbtnYs;

	private static final Font FONT_KEYPAD = new Font("Malgun Gothic", Font.BOLD, 22);
	private static final Font FONT_BUTTON = new Font("Malgun Gothic", Font.BOLD, 20);

	public SaveStampDesign(OrderDesign od) {
		super(od, "스탬프 적립", true);
		this.od = od;

		jtfTel = new JTextField();
		jtfTel.setEditable(false);
		jtfTel.setBackground(Color.WHITE);
		jtfTel.setForeground(Color.BLACK);
		jtfTel.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
		jtfTel.setHorizontalAlignment(JTextField.CENTER);
		jtfTel.setCaretColor(Color.WHITE);
		
		keyNums = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "010", "0", "←" };
		keyBtns = new ArrayList<JButton>();

		for (int i = 0; i < keyNums.length; i++) {
			JButton keyBtn = new JButton(keyNums[i]);

			keyBtn.setBackground(Color.DARK_GRAY);
			keyBtn.setForeground(Color.WHITE);
			keyBtn.setFont(FONT_KEYPAD);
			keyBtn.setFocusPainted(false);
			keyBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

			keyBtns.add(keyBtn);
		} // end for

		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(new GridLayout(4, 3));
		jpCenter.setBackground(Color.WHITE);

		for (JButton keyBtn : keyBtns) {
			jpCenter.add(keyBtn);
		} // end for

		jbtnNs = new JButton("미적립");
		jbtnNs.setBackground(Color.LIGHT_GRAY);
		jbtnNs.setForeground(Color.BLACK);
		jbtnNs.setFont(FONT_BUTTON);
		jbtnNs.setFocusPainted(false);
		jbtnNs.setBorderPainted(false);

		jbtnYs = new JButton("적립");
		jbtnYs.setBackground(Color.LIGHT_GRAY);
		jbtnYs.setForeground(Color.BLACK);
		jbtnYs.setFont(FONT_BUTTON);
		jbtnYs.setFocusPainted(false);
		jbtnYs.setBorderPainted(false);

		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(new GridLayout(1, 2, 10, 0));
		jpSouth.setBackground(Color.WHITE);

		jpSouth.add(jbtnNs);
		jpSouth.add(jbtnYs);

		jtfTel.setBounds(0, 0, 400, 101);
		jpCenter.setBounds(0, 100, 400, 390);
		jpSouth.setBounds(50, 500, 300, 50);

		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.WHITE);

		add(jtfTel);
		add(jpCenter);
		add(jpSouth);

		SaveStampEvent ue = new SaveStampEvent(od, this);
		for (JButton keyBtn : keyBtns) {
			keyBtn.addActionListener(ue);
		} // end for

		jbtnNs.addActionListener(ue);
		jbtnYs.addActionListener(ue);
		addWindowListener(ue);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.setSize(400, 600);
		this.setLocationRelativeTo(od);
		this.setVisible(true);
	}// SaveStampDesign

	public OrderDesign getOd() {
		return od;
	}// getTjf

	public JTextField getJtfTel() {
		return jtfTel;
	}// getJtfTel

	public String[] getKeyNums() {
		return keyNums;
	}// getKeyNums

	public List<JButton> getKeyBtns() {
		return keyBtns;
	}// getKeyBtns

	public JButton getJbtnNs() {
		return jbtnNs;
	}// getJbtnNs

	public JButton getJbtnYs() {
		return jbtnYs;
	}// getJbtnYs

}// class
