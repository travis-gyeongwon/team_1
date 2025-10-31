package kiosk.user.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import kiosk.user.event.TakeOutDecisionEvent;

public class TakeOutDecisionDesign extends JDialog {

	private JButton jbtnForHere, jbtnToGo;
	private static final Font FONT_BUTTON = new Font("Malgun Gothic", Font.BOLD, 30);

	public TakeOutDecisionDesign(OrderDesign od) {
		super(od, "홀/포장 선택", true);

		jbtnForHere = new JButton("홀");
		jbtnForHere.setBackground(Color.LIGHT_GRAY);
		jbtnForHere.setForeground(Color.BLACK);
		jbtnForHere.setFont(FONT_BUTTON);
		jbtnForHere.setFocusPainted(false);
		jbtnForHere.setBorderPainted(false);
		
		jbtnToGo = new JButton("포장");
		jbtnToGo.setBackground(Color.LIGHT_GRAY);
		jbtnToGo.setForeground(Color.BLACK);
		jbtnToGo.setFont(FONT_BUTTON);
		jbtnToGo.setFocusPainted(false);
		jbtnToGo.setBorderPainted(false);

		jbtnForHere.setBounds(100, 150, 200, 100);
		jbtnToGo.setBounds(100, 300, 200, 100);

		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.WHITE);

		add(jbtnForHere);
		add(jbtnToGo);

		TakeOutDecisionEvent ue = new TakeOutDecisionEvent(od, this);
		jbtnForHere.addActionListener(ue);
		jbtnToGo.addActionListener(ue);
		addWindowListener(ue);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.setSize(400, 600);
		this.setLocationRelativeTo(od);
		this.setVisible(true);
	}// TakeOutDecisionDesign

	public JButton getJbtnForHere() {
		return jbtnForHere;
	}// getJbtnForHere

	public JButton getJbtnToGo() {
		return jbtnToGo;
	}// getJbtnToGo

}// class
