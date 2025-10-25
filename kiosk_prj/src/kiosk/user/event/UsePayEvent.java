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
import kiosk.user.view.UsePayDesign;

public class UsePayEvent extends WindowAdapter implements ActionListener{
	UsePayDesign upd;
	OrderPayService ops;
	OrderProductDTO oDTO=new OrderProductDTO();
	
	boolean payFlag=false;
	
	
	
	public UsePayEvent() {
		super();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		failPay();//결제가 완료되기 전 창이 꺼지면 결제실패
		
	}//windowClosing

	public UsePayEvent(UsePayDesign upd) {
		this.upd = upd;
	}//UseCardEvent

	public void successPay() {
		
		payFlag=true;
		 
	     JOptionPane.showMessageDialog(upd,"페이 결제가 완료되었습니다.");
		 upd.dispose();
		 //영수증 창으로
		 new PrintReceiptDesign();
		}//successCard
		
		public void failPay() {
			
			payFlag=false;
		 upd.getTimer().stop();
		 if(payFlag==false) {
			 //주문 데이터 삭제(해당 주문번호의 주문테이블과 주문상세테이블 행 삭제)
			 ops=new OrderPayService();
			 ops.removeOrderDetail(oDTO.getOrderNum());//매개변수에 주문번호 orderNum들어가야함
			 ops.removeOrderList(oDTO.getOrderNum());//매개변수에 주문번호 orderNum들어가야함
			 JOptionPane.showMessageDialog(upd, "페이 결제가 취소되었습니다.");
		 }//end if
			
			upd.dispose();
			//결제가 취소되면 주문창으로
			new OrderDesign();
			
		}//failCard

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==upd.getJbtnCancel()) {//취소버튼 누르면 결제실패
				failPay();
			}//end if
			
		}//actionPerformed

}
