package kiosk.user.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import kiosk.user.event.StampInfoEvent;

public class StampInfoDesign extends JDialog {

	private SaveStampDesign ssd;
	private JTextPane jtpStampInfo;
	private JButton jbtnCheck;

	public StampInfoDesign(SaveStampDesign ssd) {
		super(ssd, "적립 완료", true);
		this.ssd = ssd;

		jtpStampInfo = new JTextPane();
		jtpStampInfo.setEditable(false);
		jtpStampInfo.setBackground(null);

		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

		StyledDocument doc = jtpStampInfo.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		jbtnCheck = new JButton("확인");

		jtpStampInfo.setBounds(0, 40, 300, 60);
		jbtnCheck.setBounds(100, 100, 100, 50);

		this.setLayout(null);
		this.setResizable(false);

		add(jtpStampInfo);
		add(jbtnCheck);

		StampInfoEvent ue = new StampInfoEvent(ssd.getOd(), this);
		jbtnCheck.addActionListener(ue);

		this.setSize(300, 200);
		this.setLocationRelativeTo(ssd);
		this.setVisible(true);
	}// StampInfoDesign

	public SaveStampDesign getSsd() {
		return ssd;
	}// getSsd

	public JTextPane getJtpStampInfo() {
		return jtpStampInfo;
	}// getJtpStampInfo

	public JButton getJbtnCheck() {
		return jbtnCheck;
	}// getJbtnCheck

}// class
