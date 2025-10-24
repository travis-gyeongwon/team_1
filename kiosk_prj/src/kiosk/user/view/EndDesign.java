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
	
	jpEnd.setSize(400,600);
	jlblOrderFinish.setBounds(90,50,300,50);
	jlblOrderNum.setBounds(70,200,300,100);
	jlblreturnToStart.setBounds(100,400,300,100);
	
	
	setSize(400,600);
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
