package kiosk.user.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import kiosk.user.event.UseCardEvent;

public class UseCardDesign extends JDialog {
	
	public JLabel jlblCardMsg;
	public OrderPayDesign opd;
	public JProgressBar progress;
	public  Timer timer;
	public JButton jbtnCancel;
	ImageIcon tempCardImg;
	
	private static final Color COLOR_BG = Color.WHITE;
	
	
	public UseCardDesign() {
		
		UseCardEvent uce=new UseCardEvent(this);
		 progress=new JProgressBar(0,100);
		 progress.setString("카드 결제 중...");
		 progress.setStringPainted(true);
		 
		 timer=new Timer(300,null);
		 timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int percent=progress.getValue()+10;
				 progress.setValue(percent);
				 if(percent>=100) {
					 timer.stop();
					 uce.successCard();//100이상 되면 결제성공으로 간주
				 }//end if
			}//actionPerformed
		});
		 
		 
		 timer.start();
		 
		try {
			tempCardImg = new ImageIcon(new URL("https://cdn-icons-png.flaticon.com/512/869/869139.png"));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Image img=tempCardImg.getImage().getScaledInstance(200, 200, DO_NOTHING_ON_CLOSE);
		ImageIcon cardImg=new ImageIcon(img);
		 JLabel jlblCardImg=new JLabel(cardImg);
		 
		 jlblCardMsg=new JLabel("휴대폰의 결제 바코드를 스캔해주세요");
		 jbtnCancel=new JButton("취소");
		 
		 jlblCardMsg=new JLabel("신용카드를 끝까지 넣어주세요");
		 jbtnCancel=new JButton("취소");
		 
		 Font payFont=new Font("나눔고딕", Font.PLAIN, 20);
		 jlblCardMsg.setFont(payFont);
		 
		    setLayout(null);
			
		    add(jlblCardImg);
		    add(jlblCardMsg);
			add(progress);
			add(jbtnCancel);
			
			jlblCardImg.setBounds(175, 133, 350, 333);
			jlblCardMsg.setBounds(180, 50, 525, 83);
			progress.setBounds(175, 500, 350, 83);
			jbtnCancel.setBounds(263, 667, 175, 83);

			addWindowListener(uce);
			jbtnCancel.addActionListener(uce);
			addWindowListener(uce);
			
			setSize(700, 1000); 
			setLocationRelativeTo(null);
			getContentPane().setBackground(COLOR_BG);
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
	}//UseCardDesign
	
	public JButton getJbtnCancel() {
		return jbtnCancel;
	}

	public Timer getTimer() {
		return timer;
	}


	public UseCardDesign(OrderPayDesign opd) {
		 super(opd,"카드 결제");
		 this.opd=opd;
	}//UseCardDesign


	
   public static void main(String[] args) {
	  new UseCardDesign();
		
	}//main

}
