package kiosk.admin.view;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import kiosk.admin.event.InvenManageEvent;

public class InvenManageDesign extends JDialog {
	
	private JList<String> jlMenuInventory; 
	private DefaultListModel<String> dlmMenuInventory; 
	private JTextField jtfFixMenu, jtfFixInventory; 
	private JButton jbtnConfirm, jbtnTotalFix;
	
	public InvenManageDesign(AdminMainDesign amd) {
		super(amd,"재고관리창",true);
		JLabel jlblInstruction = new JLabel("재고 수정하려는 메뉴를 선택해주세요");
		dlmMenuInventory = new DefaultListModel<String>();
		jlMenuInventory = new JList<String>(dlmMenuInventory);
		JScrollPane jsp = new JScrollPane(jlMenuInventory);
		JLabel jlblFixMenu = new JLabel("변경할 메뉴");
		JLabel jlblFixInventory = new JLabel("변경할 수량");
		jtfFixMenu = new JTextField();
		jtfFixInventory = new JTextField();
		jbtnConfirm = new JButton("변경");
		jbtnTotalFix = new JButton("일괄 변경");
		
		setLayout(null);
		
		jlblInstruction.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		jlMenuInventory.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		
		jlblInstruction.setBounds(40,25,500,35);
		jsp.setBounds(30,80,570,200);
		jlblFixMenu.setBounds(30,290,100,40);
		jlblFixInventory.setBounds(250,290,100,40);
		jtfFixMenu.setBounds(30,340,200,50);
		jtfFixInventory.setBounds(250,340,200,50);
		jbtnConfirm.setBounds(520,340,80,40);
		jbtnTotalFix.setBounds(30,430,100,40);
		
		add(jlblInstruction);
		add(jsp);
		add(jlblFixMenu);
		add(jlblFixInventory);
		add(jtfFixMenu);
		add(jtfFixInventory);
		add(jbtnConfirm);
		add(jbtnTotalFix);
		
		jtfFixMenu.setEditable(false);
		
		InvenManageEvent ime = new InvenManageEvent(this);
		jbtnTotalFix.addActionListener(ime);
		jbtnConfirm.addActionListener(ime);
		jlMenuInventory.addMouseListener(ime);
		ime.searchAllInven();
		
		
		setBounds(100, 100, 650, 550);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
	}// InvenManageDesign
	
	
	
	public JList<String> getJlMenuInventory() {
		return jlMenuInventory;
	}



	public DefaultListModel<String> getDlmMenuInventory() {
		return dlmMenuInventory;
	}



	public JTextField getJtfFixMenu() {
		return jtfFixMenu;
	}



	public JTextField getJtfFixInventory() {
		return jtfFixInventory;
	}



	public JButton getJbtnConfirm() {
		return jbtnConfirm;
	}



	public JButton getJbtnTotalFix() {
		return jbtnTotalFix;
	}

}// class
