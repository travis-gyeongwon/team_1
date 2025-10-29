package kiosk.admin.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import kiosk.admin.dto.OrderManageMakeOrderDTO;
import kiosk.admin.service.OrderManageService;
import kiosk.admin.view.DetailOrderDisplayDesign;
import kiosk.admin.view.OrderManageDesign;

public class OrderManageEvent extends WindowAdapter implements MouseListener, ActionListener {
	private OrderManageDesign omd;
	private OrderManageService oms;
	private ScheduledExecutorService scheduler;

	private static OrderManageEvent ome;
	
	public static OrderManageEvent getInstance(OrderManageDesign omd) {
		if(ome == null) {
			ome = new OrderManageEvent(omd);
		}// end if
		return ome;
	}//
	
	private OrderManageEvent(OrderManageDesign omd) {
		this.omd = omd;
		oms = new OrderManageService();
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(this::searchAllMakingOrder, 0, 5, TimeUnit.SECONDS);
	}// OrderManageEvent

	@Override
	public void windowClosing(WindowEvent e) {
		scheduler.shutdown();
		ome=null;
		omd.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == omd.getJbtnRenew()) {
			searchAllMakingOrder();
		}// end if
	}// actionPerformed

	@Override
	public void mouseClicked(MouseEvent e) {
		int selectedRow = omd.getJtOrderTable().getSelectedRow();
		switch(omd.getJtOrderTable().getSelectedColumn()) {
		case 5: 
			if(((String)omd.getJtOrderTable().getValueAt(selectedRow, 4)).equals("픽업 대기 중")) {
				modifyOrderStatus();
			}// end if
			else {
				JOptionPane.showMessageDialog(omd, "픽업 대기 중 상태의 주문만 픽업 완료 작업을 할 수 있습니다.");
			}// end else
			break;
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			String selectedOrderNum = (String) omd.getJtOrderTable().getValueAt(selectedRow, 0);
			String selectedOrderStatus = (String) omd.getJtOrderTable().getValueAt(selectedRow, 4);
			new DetailOrderDisplayDesign(omd, selectedOrderNum, selectedOrderStatus);
		}// end switch
	}// mouseClicked

	public void searchAllMakingOrder() {
		List<OrderManageMakeOrderDTO> listMakeOrder = new ArrayList<OrderManageMakeOrderDTO>();
		listMakeOrder = oms.searchAllMakingOrder();
		omd.getDtmOrderTable().setRowCount(0);
		String[] rowData = null;
		for (OrderManageMakeOrderDTO ommoDTO : listMakeOrder) {
			rowData = new String[6];
			rowData[0] = ommoDTO.getOrderNum();
			rowData[1] = new SimpleDateFormat("hh:mm:ss").format(ommoDTO.getOrderTime());
			rowData[2] = ommoDTO.getRepresentMenu();	
			rowData[3] = String.valueOf(ommoDTO.getPrice());
			rowData[4] = ommoDTO.getOrderStatus();
			rowData[5] = "픽업 완료 시 클릭";
			omd.getDtmOrderTable().addRow(rowData);
		} // end for
		
		// 주문 목록이 비어있을 경우 안내 문구 추가
	    if (omd.getDtmOrderTable().getRowCount() == 0) {
	    	omd.getJlblInstruction().setText("현재 진행 중인 주문이 없습니다.");
	    }// end if
	    else {
	    	omd.getJlblInstruction().setText("관리할 주문을 선택해주세요");
	    }// end else
		omd.getJlblRenewTime().setText("최근 갱신 시간 : "+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		
	}// searchAllMakingOrder

	public void modifyOrderStatus() {
		int selectedRow = omd.getJtOrderTable().getSelectedRow();
		String selectedOrderNum = (String) omd.getJtOrderTable().getValueAt(selectedRow, 0);
		oms.modifyOrderStatus(selectedOrderNum);
		searchAllMakingOrder();
	}// modifyOrderStatus
	
	
	
	

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

}// class
