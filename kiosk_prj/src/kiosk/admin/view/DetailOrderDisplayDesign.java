package kiosk.admin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kiosk.admin.event.DetailOrderDisplayEvent;

public class DetailOrderDisplayDesign extends JDialog {
	private JLabel jlblinstruction;
	private JTextArea jtaDetailOrder;
	private JButton jbtnOrderConfirm, jbtnFinshProduct; 
	private String selectedOrderNum;
	private OrderManageDesign omd;
	
	public DetailOrderDisplayDesign(OrderManageDesign omd, String selectedOrderNum, String selectedOrderStatus) {
		super(omd,"주문 세부창",true);
		this.omd = omd;
		jlblinstruction = new JLabel();
		this.selectedOrderNum=selectedOrderNum;
		jtaDetailOrder = new JTextArea();
		jtaDetailOrder.setEditable(false);
		JScrollPane jsp = new JScrollPane(jtaDetailOrder);
		jbtnOrderConfirm = new JButton("주문 확인");
		jbtnFinshProduct = new JButton("제조 완료");
		jbtnOrderConfirm.setEnabled(false);
		jbtnFinshProduct.setEnabled(false);
		
		jlblinstruction.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		
		setLayout(null);
		
		jlblinstruction.setBounds(30,20,420,35);
		jsp.setBounds(30,60,390,170);
		jbtnOrderConfirm.setBounds(30,250,100,40);
		jbtnFinshProduct.setBounds(150,250,100,40);
		
		add(jlblinstruction);
		add(jsp);
		add(jbtnOrderConfirm);
		add(jbtnFinshProduct);
		
		DetailOrderDisplayEvent dode = new DetailOrderDisplayEvent(this, selectedOrderNum);
		jbtnOrderConfirm.addActionListener(dode);
		jbtnFinshProduct.addActionListener(dode);
		dode.setOrderDetial(selectedOrderNum);
		
		setBounds(omd.getX()+50, omd.getY()+50, 450, 350);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}// DetailOrderDisplayDesign


	public JLabel getJlblinstruction() {
		return jlblinstruction;
	}


	public JTextArea getJtaDetailOrder() {
		return jtaDetailOrder;
	}


	public JButton getJbtnOrderConfirm() {
		return jbtnOrderConfirm;
	}


	public JButton getJbtnFinshProduct() {
		return jbtnFinshProduct;
	}


	public String getSelectedOrderNum() {
		return selectedOrderNum;
	}


	public OrderManageDesign getOmd() {
		return omd;
	}
	
	
	
}// class
