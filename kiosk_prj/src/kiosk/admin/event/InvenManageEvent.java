package kiosk.admin.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kiosk.admin.dto.InvenManageDTO;
import kiosk.admin.service.InvenManageService;
import kiosk.admin.view.InvenManageDesign;

public class InvenManageEvent implements ActionListener, MouseListener {
	private InvenManageDesign imd;
	private InvenManageService ims;

	public InvenManageEvent(InvenManageDesign imd) {
		this.imd = imd;
		ims = new InvenManageService();
	}// InvenManageEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == imd.getJbtnTotalFix()) {
			modifyAllInventory();
		}// end if
		if(e.getSource() == imd.getJbtnConfirm()) {
			modifyOneInventory();
		}// end if
	}// InvenManageDesign

	@Override
	public void mouseClicked(MouseEvent e) {
		String selectMenuColmn =imd.getJlMenuInventory().getSelectedValue();
		String menuName = selectMenuColmn.substring(0, selectMenuColmn.indexOf("-")-1);
		String menuCnt = selectMenuColmn.substring(selectMenuColmn.indexOf("-")+2);
		imd.getJtfFixMenu().setText(menuName);
		imd.getJtfFixInventory().setText(menuCnt);
		
	}// mouseClicked

	public void searchAllInven() {
		DefaultListModel<String> dtm = imd.getDlmMenuInventory();
		String listEle = "";
		List<InvenManageDTO> listImDTO = new ArrayList<InvenManageDTO>();
		listImDTO = ims.searchAllInventory();
		StringBuilder sb = null;
		dtm.removeAllElements();
		for(InvenManageDTO imDTO : listImDTO) {
			sb = new StringBuilder();
			listEle = sb.append(imDTO.getMenuName()).append(" - ").append(imDTO.getMenuInventory()).toString();
			dtm.addElement(listEle);
		}// end for
		
	}// searchAllInven

	public void modifyAllInventory() {
		int flag = ims.modifyAllInventory();
		String outputMsg = flag + "개의 메뉴의 수량을 변경하였습니다.";
		switch (flag) {
		case 0:
			outputMsg = "변경에 실패했습니다.";
			break;
		}// end switch
		JOptionPane.showMessageDialog(imd, outputMsg);
		// 변경된 정보를 반영
		searchAllInven();
	}// modifyAllInventory()
	
	
	public void modifyOneInventory() {
		String menuName = imd.getJtfFixMenu().getText().trim();
		try {
			int quantity = Integer.parseInt(imd.getJtfFixInventory().getText().trim());
			if(quantity < 0) {
				JOptionPane.showMessageDialog(imd, "수량은 정수로 입력해주세요");
				return;
			}// end if
			if(quantity > 9999) {
				JOptionPane.showMessageDialog(imd, "9999개까지 입력 가능합니다.");
				return;
			}// end if
			InvenManageDTO imDTO = new InvenManageDTO(menuName, quantity);
			int flag = ims.modifyOneInventory(imDTO);
			String outputMsg = flag + "개의 메뉴의 수량을 변경하였습니다.";
			switch (flag) {
			case 0:
				outputMsg = "변경에 실패했습니다.";
				break;
			}// end switch
			JOptionPane.showMessageDialog(imd, outputMsg);
			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(imd, "수량은 정수로 입력해주세요");
			return;
		}// end catch
		searchAllInven();
	}// modifyOneInventory
	
	
	
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
