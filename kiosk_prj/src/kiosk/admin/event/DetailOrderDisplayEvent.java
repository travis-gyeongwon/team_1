package kiosk.admin.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import kiosk.admin.dto.DetailOrderDisplayOrderDTO;
import kiosk.admin.dto.DetailOrderDisplayProductDTO;
import kiosk.admin.service.DetailOrderDisplayService;
import kiosk.admin.view.DetailOrderDisplayDesign;

public class DetailOrderDisplayEvent implements ActionListener{
	
	private DetailOrderDisplayDesign dodd;
	private DetailOrderDisplayService dods;
	
	public DetailOrderDisplayEvent(DetailOrderDisplayDesign dodd, String selectedOrderNum) {
		this.dodd = dodd;
		dods = new DetailOrderDisplayService();
		setOrderDetial(selectedOrderNum);
		
		
	}// DetailOrderDisplayEvent
	
	public void setOrderDetial(String selectedOrderNum) {
		DetailOrderDisplayOrderDTO dodoDTO = dods.searchOneMakingDetailOrder(selectedOrderNum);
		String instruction = dodoDTO.getOrderNum()+" - "+dodoDTO.getOrderStatus();
		dodd.getJlblinstruction().setText(instruction);
		StringBuilder sb = new StringBuilder();
		sb
		.append("주문 번호 : ").append(dodoDTO.getOrderNum()).append("\n")
		.append("주문 시간 : ").append(new SimpleDateFormat("hh:mm:ss yyyy-MM-dd").format(dodoDTO.getOrderTime())).append("\n");
		for(DetailOrderDisplayProductDTO dodpDTO : dodoDTO.getDetailMenu()) {
			sb
			.append(dodpDTO.getTempOption()).append(" ")// 온도 옵션 표시
			.append(dodpDTO.getMenuName()).append(" ")// 메뉴 이름 표시
			.append(dodpDTO.getSizeOption()).append(" ")// 사이즈 옵션 표시
			.append(dodpDTO.getShotOption()).append(" ")// 샷 옵션 표시
			.append(dodpDTO.getAmount()).append("개\n");// 수량 표시
		}// end for
		sb
		.append("포장 여부 : ").append(dodoDTO.getTakeOutFlag()).append("\n")
		.append(dodoDTO.getPrice()).append("원");// 가격 표시
		dodd.getJtaDetailOrder().setText(sb.toString());
		
		if(dodoDTO.getOrderStatus().equals("확인 대기 중")){
			dodd.getJbtnOrderConfirm().setEnabled(true);
		}// end if
		
		if(dodoDTO.getOrderStatus().equals("음료 제조 중")) {
			dodd.getJbtnFinshProduct().setEnabled(true);
		}// end if
		
	}// setOrderDetial
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ins = dodd.getJlblinstruction().getText();
		String selectedOrderNum = ins.substring(0,ins.indexOf("-")-1 ).trim();
		dods.modifyOrderStatus(selectedOrderNum);

		OrderManageEvent ome = OrderManageEvent.getInstance(dodd.getOmd());
		ome.searchAllMakingOrder();
		ome=null;
		dodd.dispose();
	}// actionPerformed
}// class 
