package kiosk.user.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import kiosk.user.event.JoinDecisionEvent;

public class JoinDecisionDesign extends JDialog {

	private SaveStampDesign ssd;
	private JTextPane jtpMsg;
	private JButton jbtnNj, jbtnYj;

	public JoinDecisionDesign(SaveStampDesign ssd) {
		super(ssd, "회원 가입", true);
		this.ssd=ssd;

		jtpMsg = new JTextPane();
		jtpMsg.setText("회원 정보가 없습니다.\n입력하신 전화번호로 가입하시겠습니까?");
		jtpMsg.setEditable(false);
		jtpMsg.setBackground(null);

		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		
		StyledDocument doc = jtpMsg.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		jbtnNj = new JButton("아니오");
		jbtnYj = new JButton("예");

		jtpMsg.setBounds(0, 30, 300, 70);
		jbtnNj.setBounds(30, 100, 100, 50);
		jbtnYj.setBounds(160, 100, 100, 50);

		this.setLayout(null);
		this.setResizable(false);

		add(jtpMsg);
		add(jbtnNj);
		add(jbtnYj);
		
		JoinDecisionEvent ue=new JoinDecisionEvent(this);
		jbtnNj.addActionListener(ue);
		jbtnYj.addActionListener(ue);

		this.setSize(300, 200);
		this.setLocationRelativeTo(ssd);
		this.setVisible(true);
	}// JoinDecisionDesign
	
	public SaveStampDesign getSsd() {
		return ssd;
	}// getSsd

	public JButton getJbtnNj() {
		return jbtnNj;
	}// getJbtnNj

	public JButton getJbtnYj() {
		return jbtnYj;
	}// getJbtnYj

}// class
