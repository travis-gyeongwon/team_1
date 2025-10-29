package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.dto.OrderProductDTO;
import kiosk.user.service.OrderPayService;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.PrintDecisionDesign;
import kiosk.user.view.PrintReceiptDesign;
import kiosk.user.view.UsePayDesign;

public class UsePayEvent extends WindowAdapter implements ActionListener{
	UsePayDesign upd;
	OrderPayService ops;
	
	boolean payFlag=false;
	
	
	
	public UsePayEvent() {
		super();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		upd.getTimer().stop();
		 ops=new OrderPayService();
		 ops.removeOrderDetail();
		 ops.removeOrderList();
		 JOptionPane.showMessageDialog(upd, "페이 결제가 취소되었습니다.");
		
		
	}//windowClosing

	public UsePayEvent(UsePayDesign upd) {
		this.upd = upd;
	}//UseCardEvent

	public void successPay() {
		
		payFlag=true;
		 
	     JOptionPane.showMessageDialog(upd,"페이 결제가 완료되었습니다.");
		 upd.dispose();
		 upd.getOd().dispose();
		 //영수증 창으로
		 new PrintDecisionDesign();
		}//successCard
		
		public void failPay() {
			
			payFlag=false;
		 upd.getTimer().stop();
		 if(payFlag==false) {
			 //주문 데이터 삭제(해당 주문번호의 주문테이블과 주문상세테이블 행 삭제)
			 ops=new OrderPayService();
			 ops.removeOrderDetail();
			 ops.removeOrderList();
			 JOptionPane.showMessageDialog(upd, "페이 결제가 취소되었습니다.");
			 upd.dispose();
		 }//end if
			
			//결제가 취소되면 주문창으로
			
		}//failCard

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==upd.getJbtnCancel()) {//취소버튼 누르면 결제실패
				failPay();
			}//end if
			
		}//actionPerformed

}
