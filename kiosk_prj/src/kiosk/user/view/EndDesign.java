package kiosk.user.view;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kiosk.user.event.EndEvent;
import kiosk.user.service.OrderPayService;

public class EndDesign extends JDialog{
	
	public JLabel jlblOrderFinish;
	public JLabel jlblOrderNum;
	public JLabel jlblreturnToStart;
	public JPanel jpEnd;
	OrderPayService ops=new OrderPayService();
	
	private static final Color COLOR_BG = Color.WHITE;
	
	public EndDesign() {
		
	jpEnd=new JPanel();
	jlblOrderFinish=new JLabel("주문이 완료되었습니다");
	jlblOrderNum=new JLabel("주문번호: "+ops.showMaxOrderNum());
	jlblreturnToStart=new JLabel("화면을 터치하면 처음으로 돌아갑니다");
	
	
	Font font=new Font("맑은 고딕",Font.BOLD,30);
	jlblOrderFinish.setFont(font);
	jlblOrderNum.setFont(font);
	
	jlblreturnToStart.setFont(new Font("맑은 고딕", Font.BOLD, 20));
	
	
	EndEvent ee=new EndEvent(this);
	jpEnd.addMouseListener(ee);
	addWindowListener(ee);
	
	setLayout(null);
	jpEnd.setLayout(null);
	jpEnd.add(jlblOrderFinish);
	jpEnd.add(jlblOrderNum);
	jpEnd.add(jlblreturnToStart);
	
	
	add(jpEnd);
	
	jpEnd.setSize(700,1000);
	jpEnd.setBackground(COLOR_BG);
	jlblOrderFinish.setBounds(190, 83, 525, 83);
	jlblOrderNum.setBounds(180, 333, 525, 167);
	jlblreturnToStart.setBounds(180, 667, 525, 167);

	
	
	setSize(700, 1000); 
	getContentPane().setBackground(COLOR_BG);
	setLocationRelativeTo(null);
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
