package kiosk.user.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.service.StartService;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.StartDesign;

public class StartEvent extends WindowAdapter implements MouseListener {

	public StartDesign sd;
	
	public StartEvent(){
		
	}//StartEvent
	
	public StartEvent(StartDesign sd) {
		this.sd=sd;
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		sd.dispose();
		
	}//windowClosing

	@Override
	public void mouseClicked(MouseEvent e) {
		
		StartService ss=new StartService();
		if(e.getSource()==sd.getJpStart()) {
			
			if(ss.showStoreStatus()==true) {
				//메뉴선택창 띄우기
				new OrderDesign();
				sd.dispose();
				
			}else {
				//터치 안되도록
				JOptionPane.showMessageDialog(sd, "영업시간이 아닙니다");
			}//end else
			
		}//end if
		
	}//mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	
}//class
