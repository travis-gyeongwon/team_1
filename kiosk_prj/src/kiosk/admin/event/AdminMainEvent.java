package kiosk.admin.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kiosk.admin.service.StoreStatusService;
import kiosk.admin.view.AdminMainDesign;
import kiosk.admin.view.InvenManageDesign;
import kiosk.admin.view.MenuManageDesign;
import kiosk.admin.view.OrderManageDesign;
import kiosk.admin.view.SalesReportDesign;

public class AdminMainEvent extends WindowAdapter implements ActionListener {
	private AdminMainDesign amd;
	
	private StoreStatusService sss;
	private String open;
	
	public AdminMainEvent(AdminMainDesign amd) {
		this.amd = amd;
		//open 설정해주는 곳. 테이블에서 값을 가져와야함.
		sss = new StoreStatusService();
		try {
			open = sss.searchStoreStatus(amd.getlDTO().getId());
			setStoreStatus();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == amd.getJbtnMenu()) {
			openMenuManager();
		}
		if(ae.getSource() == amd.getJbtnOrder()) {
			openOrderManager();
		}
		if(ae.getSource() == amd.getJbtnInven()) {
			openInventoryManager();
		}
		if(ae.getSource() == amd.getJbtnSales()) {
			openSalesReportManager();
		}
		if(ae.getSource() == amd.getJbtnOpen()) {
			storeOpen();
		}
		if(ae.getSource() == amd.getJbtnClose()) {
			storeClose();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		if("Y".equals(open)) {
			int select = JOptionPane.showConfirmDialog(amd, "영업 중입니다. 영업을 종료하고 닫으시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
			if(select == JOptionPane.YES_OPTION) {
				storeClose();
			}else {
			int selectClose = JOptionPane.showConfirmDialog(amd, "영업 중입니다. 화면을 닫으시겠습니까?","종료 확인",JOptionPane.YES_NO_OPTION);
			if(selectClose == JOptionPane.YES_OPTION)
				amd.dispose();
			}
		}else {
			amd.dispose();
		}
	}
	
	//버튼 눌렀을 때 해당 화면 생성 method 들
	private void openMenuManager() {
		new MenuManageDesign();
	}
	
	private void openOrderManager() {
		new OrderManageDesign(amd);
	}
	
	private void openInventoryManager() {
		new InvenManageDesign(amd);
	}
	
	private void openSalesReportManager() {
		new SalesReportDesign(amd);
		
	}
	
	private void storeOpen() {
		String message = "영업 중 입니다.";
		if("N".equals(open)) {
			open = "Y";
			if(sss.modifyStoreStatus(open,amd.getlDTO().getId())!=1) {
				message = "영업을 시작할 수 없습니다.";
				open = "N";
			}else {
				message = "영업을 시작합니다.";
				amd.getJlStatus().setText("영업 상태 : 영업 중");
				openOrderManager();
			}
		}
		
		JOptionPane.showMessageDialog(amd, message);
	}
	
	private void storeClose() {
		String message = "영업 중이 아닙니다.";
		if("Y".equals(open)) {
			open = "N";
			if(sss.modifyStoreStatus(open,amd.getlDTO().getId())!=1) {
				message = "영업을 종료할 수 없습니다.";
				open = "Y";//DB 오류이므로 DB는 변경되지 않은 상태일 것이라 예상..
			}else {
				message = "영업을 종료합니다.";
				amd.getJlStatus().setText("영업 상태 : 영업 종료");
			}
		}
		
		JOptionPane.showMessageDialog(amd, message);
	}
	
	private void setStoreStatus() {
		if("Y".equals(open)) {
			amd.getJlStatus().setText("영업 상태 : 영업 중");
		}else {
			amd.getJlStatus().setText("영업 상태 : 영업 종료");
		}
	}
}
