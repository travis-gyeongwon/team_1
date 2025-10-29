package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

import kiosk.user.dto.OrderPayDTO;
import kiosk.user.dto.OrderProductDTO;
import kiosk.user.service.EndService;
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
	     
	     
	     OrderPayService ops = new OrderPayService();
			List<OrderPayDTO> temp=ops.showOrderDetail();
			EndService es=new EndService();
				//매개변수에 orderNum
				for(OrderPayDTO opDTO : temp) {
					es.changeInventory(opDTO.getMenuName(),opDTO.getAmount());//재고 변경
					
				}
				
				//매개변수에 주문번호 orderNum
				es.changeOrderStatus(ops.showMaxOrderNum());//주문상태 변경(확인 대기 중으로)
	     
	     
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
