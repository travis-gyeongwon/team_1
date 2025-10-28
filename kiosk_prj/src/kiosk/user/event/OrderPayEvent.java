package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.user.service.OrderPayService;
import kiosk.user.service.StartService;
import kiosk.user.view.OrderDesign;
import kiosk.user.view.OrderPayDesign;
import kiosk.user.view.SaveStampDesign;
import kiosk.user.view.UseCardDesign;
import kiosk.user.view.UsePayDesign;

public class OrderPayEvent extends WindowAdapter implements ActionListener{

	public OrderPayDesign opd;
	OrderPayService ops=new OrderPayService();
	StartService ss=new StartService();
	
	public OrderPayEvent() {
		super();
	}
	
	

	public OrderPayEvent(OrderPayDesign opd) {
		this.opd = opd;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		ops=new OrderPayService();
		
		//버튼을 눌렀을 때 결제방식 checkout 업데이트
		if(e.getSource()==opd.getJbtnCard()) {//카드 버튼 
			
			if(ss.showStoreStatus()==true) {//영업중이면
				ops.changeCheckout(ops.showMaxOrderNum(),1);//checkout 1은 카드
				opd.dispose();
				new UseCardDesign();
			}else {//영업중이 아니면
				JOptionPane.showMessageDialog(opd, "영업 중이 아닙니다");
			}
		}//end if
		
		if(e.getSource()==opd.getJbtnPay()) {//페이 버튼
			if(ss.showStoreStatus()==true) {//영업 중이면
				ops.changeCheckout(ops.showMaxOrderNum(),2);//checkout 2는 페이
				opd.dispose();
				new UsePayDesign();
			}else {//영업 중이 아니면
				JOptionPane.showMessageDialog(opd, "영업 중이 아닙니다");
			}
		}//end if
		
		
		if(e.getSource()==opd.getJbtnBefore()) {//이전 버튼
			opd.dispose();
			//적립창으로 돌아가게
			new SaveStampDesign(opd.getOd());
		}//end if
	}//actionPerformed



	@Override
	public void windowClosing(WindowEvent e) {
		ops.removeOrderDetail();
		ops.removeOrderList();
		opd.dispose();
	}//windowClosing
	
	

}//class
