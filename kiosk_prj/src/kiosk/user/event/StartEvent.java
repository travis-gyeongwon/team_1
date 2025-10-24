package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		//만약 화면 터치(클릭)이 아닌 X로 창이 꺼졌다면 영업종료창 떠야함
		
	}//windowClosing

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==sd.getJpStart()) {
			//메뉴선택창 띄우기
			sd.dispose();
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
