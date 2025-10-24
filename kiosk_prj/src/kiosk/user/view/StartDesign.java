package kiosk.user.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kiosk.user.event.StartEvent;
import kiosk.user.service.StartService;

public class StartDesign extends JDialog{
	
	public ImageIcon recommendMenuImg;
	public JLabel jlblRecommendMenu;
	public JLabel jlblScreenStart;
	public JPanel jpStart;
	StartService ss;
	
	public StartDesign() {
	
		jpStart=new JPanel();
		jlblScreenStart=new JLabel("화면을 터치해주세요");
	
		Font font=new Font("나눔고딕",Font.BOLD,20);
		jlblScreenStart.setFont(font);
		
//		recommendMenuImg=new ImageIcon(ss.showBestmenu());
//		jlblRecommendMenu=new JLabel(recommendMenuImg);
		
	jpStart.add(jlblScreenStart);
//	jpStart.add(jlblRecommendMenu);
	
	
	
	jpStart.setSize(400,600);

	add(jpStart);
	
	setLayout(null);
	jpStart.setLayout(null);
	
	getJlblScreenStart().setBounds(85,480,300,100);
	
	
	StartEvent se=new StartEvent(this);
	jpStart.addMouseListener(se);
	
	setSize(400,600);
	setLocation(800,300);
	setVisible(true);
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}//StartDesign


	public ImageIcon getRecommendMenuImg() {
		return recommendMenuImg;
	}


	public JLabel getJlblRecommendMenu() {
		return jlblRecommendMenu;
	}


	public JLabel getJlblScreenStart() {
		return jlblScreenStart;
	}


	public JPanel getJpStart() {
		return jpStart;
	}
	
	public static void main(String[] args) {
		new StartDesign();
	}
	

}
