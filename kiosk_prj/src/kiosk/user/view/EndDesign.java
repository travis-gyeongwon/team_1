package kiosk.user.view;


import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kiosk.user.event.EndEvent;

public class EndDesign extends JDialog{
	
	public JLabel jlblOrderFinish;
	public JLabel jlblOrderNum;
	public JLabel jlblreturnToStart;
	public JPanel jpEnd;
	
	public EndDesign() {
		
		jpEnd=new JPanel();
	jlblOrderFinish=new JLabel("주문이 완료되었습니다");
	jlblOrderNum=new JLabel("주문번호: ");
	jlblreturnToStart=new JLabel("화면을 터치하면 처음으로 돌아갑니다");
	
	
	Font font=new Font("나눔고딕",Font.BOLD,20);
	jlblOrderFinish.setFont(font);
	jlblOrderNum.setFont(font);
	
	
	EndEvent ee=new EndEvent(this);
	jpEnd.addMouseListener(ee);
	
	setLayout(null);
	jpEnd.setLayout(null);
	jpEnd.add(jlblOrderFinish);
	jpEnd.add(jlblOrderNum);
	jpEnd.add(jlblreturnToStart);
	
	
	add(jpEnd);
	
	jpEnd.setSize(700,1000);
	jlblOrderFinish.setBounds(158, 83, 525, 83);
	jlblOrderNum.setBounds(123, 333, 525, 167);
	jlblreturnToStart.setBounds(175, 667, 525, 167);

	
	
	setSize(700, 1000); 
	setLocationRelativeTo(null);
	setLocation(800,300);
	setVisible(true);
	
		
	}//EndDesign

	public JLabel getJlblOrderFinish() {
		return jlblOrderFinish;
	}

	public JLabel getJlblOrderNum() {
		return jlblOrderNum;
	}

	public JPanel getJpEnd() {
		return jpEnd;
	}
	
	
	public static void main(String[] args) {
		new EndDesign();
	}
	
	

}//class
