package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.dto.OrderProductDTO;
import kiosk.user.service.OrderPayService;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.PrintReceiptDesign;
import kiosk.user.view.UseCardDesign;

public class UseCardEvent extends WindowAdapter implements ActionListener{
	
	UseCardDesign ucd;
	OrderPayService ops;
	
	boolean cardFlag=false;
	
	
	
	public UseCardEvent() {
		super();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ucd.getTimer().stop();
		 ops=new OrderPayService();
		 ops.removeOrderDetail();
		 ops.removeOrderList();
		 JOptionPane.showMessageDialog(ucd, "카드 결제가 취소되었습니다.");
		
	}//windowClosing

	public UseCardEvent(UseCardDesign ucd) {
		this.ucd = ucd;
	}//UseCardEvent

	public void successCard() {
		
		 cardFlag=true;
		 
	     JOptionPane.showMessageDialog(ucd,"카드 결제가 완료되었습니다.");
		 ucd.dispose();
		 //영수증 창으로
		 new PrintReceiptDesign();
		 
		}//successCard
		
		public void failCard() {
			
			cardFlag=false;
		 ucd.getTimer().stop();
		 if(cardFlag==false) {
			 //주문 데이터 삭제(해당 주문번호의 주문테이블과 주문상세테이블 행 삭제)
			 ops=new OrderPayService();
			 ops.removeOrderDetail();
			 ops.removeOrderList();
			 JOptionPane.showMessageDialog(ucd, "카드 결제가 취소되었습니다.");
			 
			 ucd.dispose();
			//결제가 취소되면 주문창으로
		 }//end if
			
		}//failCard

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==ucd.getJbtnCancel()) {//취소버튼 누르면 결제실패
				failCard();
			}//end if 
			
		}//actionPerformed


}
