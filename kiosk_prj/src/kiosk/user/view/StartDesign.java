package kiosk.user.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

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
	
	private static final Color COLOR_BG = Color.WHITE;
	
	public StartDesign() {
		
	ss=new StartService();
		jpStart=new JPanel();
		jlblScreenStart=new JLabel("화면을 터치해주세요");
	
		Font font=new Font("나눔고딕",Font.BOLD,20);
		jlblScreenStart.setFont(font);
		
		ImageIcon originalMenuImg=new ImageIcon();
		originalMenuImg=ss.showBestmenu();
		
		Image resize=originalMenuImg.getImage().getScaledInstance(200, 200, DO_NOTHING_ON_CLOSE);
		recommendMenuImg=new ImageIcon(resize);
		
		jlblRecommendMenu=new JLabel(recommendMenuImg);
		
	jpStart.add(jlblScreenStart);
	jpStart.add(jlblRecommendMenu);
	
	
	
	jpStart.setSize(700,1000);

	add(jpStart);
	
	setLayout(null);
	jpStart.setLayout(null);
	jpStart.setBackground(COLOR_BG);
	
	jlblScreenStart.setBounds(250,700,300,50);
	jlblRecommendMenu.setBounds(150,100,400,600);
	
	
	StartEvent se=new StartEvent(this);
	jpStart.addMouseListener(se);
	
	setSize(700, 1000); 
	setLocationRelativeTo(null);
	getContentPane().setBackground(COLOR_BG);
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
