package kiosk.user.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import kiosk.user.event.UsePayEvent;

public class UsePayDesign extends JDialog {
	public JLabel jlblPayMsg;
	public OrderPayDesign opd;
	public JProgressBar progress;
	public  Timer timer;
	public JButton jbtnCancel;
	ImageIcon tempPayImg;
	
	
	
	public UsePayDesign() {
		
		UsePayEvent upe=new UsePayEvent(this);
		 progress=new JProgressBar(0,100);
		 progress.setString("페이 결제 중...");
		 progress.setStringPainted(true);
		 
		 timer=new Timer(300,null);
		 timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int percent=progress.getValue()+10;
				 progress.setValue(percent);
				 if(percent>=100) {
					 timer.stop();
					 upe.successPay();//100이상 되면 결제성공으로 간주
				 }//end if
			}//actionPerformed
		});
		 
		 
		 timer.start();
		 
		 
		 try {
			 tempPayImg = new ImageIcon(new URL("https://png.pngtree.com/png-vector/20190411/ourlarge/pngtree-vector-barcode-icon-png-image_927084.jpg"));
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Image img=tempPayImg.getImage().getScaledInstance(200, 200, DO_NOTHING_ON_CLOSE);
			ImageIcon payImg=new ImageIcon(img);
			 JLabel jlblPayImg=new JLabel(payImg);
		 
		 jlblPayMsg=new JLabel("<html>휴대폰의 페이 결제 바코드를 <br>스캔해주세요");
		 jbtnCancel=new JButton("취소");
		 
		 Font payFont=new Font("나눔고딕", Font.PLAIN, 20);
		 jlblPayMsg.setFont(payFont);
		 
		 setLayout(null);
			
		    add(jlblPayImg);
		    add(jlblPayMsg);
			add(progress);
			add(jbtnCancel);
			
			jlblPayImg.setBounds(100,80,200,200);
			jlblPayMsg.setBounds(50,30,300,50);
			progress.setBounds(100,300,200,50);
			jbtnCancel.setBounds(150,400,100,50);
			
			addWindowListener(upe);
			jbtnCancel.addActionListener(upe);
			
			setSize(400,600);
			setLocation(800,300);
			setVisible(true);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
	}//UseCardDesign
	
	public Timer getTimer() {
		return timer;
	}


	public UsePayDesign(OrderPayDesign opd) {
		 super(opd,"카드 결제");
		 this.opd=opd;
	}//UseCardDesign


	
   public JButton getJbtnCancel() {
		return jbtnCancel;
	}

   public static void main(String[] args) {
	  new UsePayDesign();
		
	}//main

}//class
