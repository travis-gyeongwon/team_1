package kiosk.user.view;

import javax.swing.JButton;
import javax.swing.JDialog;

import kiosk.user.event.TakeOutDecisionEvent;

public class TakeOutDecisionDesign extends JDialog {

	private JButton jbtnForHere, jbtnToGo;

	public TakeOutDecisionDesign(OrderDesign od) {
		super(od, "홀/포장 선택", true);

		jbtnForHere = new JButton("홀");
		jbtnToGo = new JButton("포장");

		jbtnForHere.setBounds(100, 150, 200, 100);
		jbtnToGo.setBounds(100, 300, 200, 100);

		this.setLayout(null);
		this.setResizable(false);

		add(jbtnForHere);
		add(jbtnToGo);

		TakeOutDecisionEvent ue = new TakeOutDecisionEvent(od, this);
		jbtnForHere.addActionListener(ue);
		jbtnToGo.addActionListener(ue);

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
