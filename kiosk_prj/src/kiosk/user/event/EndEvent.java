package kiosk.user.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kiosk.user.dto.OrderProductDTO;
import kiosk.user.service.EndService;
import kiosk.user.view.EndDesign;
import kiosk.user.view.StartDesign;

public class EndEvent extends WindowAdapter implements MouseListener{

	EndDesign ed;
	EndService es;
	OrderProductDTO oDTO=new OrderProductDTO();
	
	
	public EndEvent() {
		
	}//EndEvent


	public EndEvent(EndDesign ed) {
		this.ed = ed;
	}//EndEvent


	

	@Override
	public void mouseClicked(MouseEvent e) {

		es=new EndService();
		if(e.getSource()==ed.getJpEnd()) {//패널 화면 클릭하면 
			//매개변수에 메뉴이름
			es.changeInventory();//재고 변경
			
			//매개변수에 주문번호 orderNum
			es.changeOrderStatus(oDTO.getOrderNum());//주문상태 변경(확인 대기 중으로)
			
			ed.dispose();//현재 창 꺼지고
			new StartDesign();//첫 화면으로 돌아가기
		}//end if
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		ed.dispose();
	}//windowClosing

	
	
}
