package kiosk.user.view;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kiosk.user.event.OrderPayEvent;
import kiosk.user.dto.OrderPayDTO;
import kiosk.user.service.OrderPayService;

public class OrderPayDesign extends JFrame{

	DefaultTableModel dtm;
	JTable jtOrderList;
	public JLabel jlblAmountPrice;
	public JButton jbtnCard;
	public JButton jbtnPay;
	public JButton jbtnBefore;
	OrderPayService ops;
	
	
	



	public OrderPayDesign() {
		
		//추후 orderNum 받아옴
		
    	String orderNum="2510160001";
	    ops=new OrderPayService();
	    String[]tableColumn={"메뉴명","수량","금액"};
	    
	    dtm=new DefaultTableModel(tableColumn, 0);	
		jtOrderList=new JTable(dtm);
        jtOrderList.setEnabled(false);
		
		
		List<OrderPayDTO>orderDetailList=ops.showOrderDetail(orderNum);
			for(OrderPayDTO opDTO : orderDetailList) {
				String menu=
						opDTO.getMenuName()+"("+opDTO.getTempOption()+","
								+opDTO.getSizeOption()+","+opDTO.getShotOption()+")";
				
				String	menuAmount=String.valueOf(opDTO.getAmount());
				
				String	price=String.valueOf(opDTO.getOrderPrice());
				
				//테이블에 행 추가
				String[]row= {menu,menuAmount,price};
				dtm.addRow(row);
				
			}//end for
		
			jtOrderList.getColumn("메뉴명").setPreferredWidth(230);

        JScrollPane scroll=new JScrollPane(jtOrderList);
	
        int[]total=ops.amountPriceTotal(orderNum);
		String quantity=String.valueOf(total[0]);
		String price=String.valueOf(total[1]);
		
		String amountPrice="총 수량: "+quantity+"개 총 금액: "+price+"원";
		jlblAmountPrice=new JLabel(amountPrice);
		
		Font font=new Font("맑은고딕",Font.BOLD,20);
		
		JLabel orderTitle=new JLabel("주문내역");
		JLabel jlblChoosePayMethod=new JLabel("결제방식을 선택해주세요!");
		
		jbtnCard=new JButton("카드");
		jbtnPay=new JButton("페이");
		jbtnBefore=new JButton("이전");
		
		OrderPayEvent ope=new OrderPayEvent(this);
		jbtnCard.addActionListener(ope);
		jbtnPay.addActionListener(ope);
		jbtnBefore.addActionListener(ope);
		
		setLayout(null);
		orderTitle.setFont(font);
		jlblAmountPrice.setFont(font);
		orderTitle.setBounds(150,10,100,50);
		scroll.setBounds(20,50,350,250);
		jlblAmountPrice.setBounds(50,300,300,50);
		jlblChoosePayMethod.setBounds(120,350,300,50);
		jbtnCard.setBounds(70,400,100,50);
		jbtnPay.setBounds(220,400,100,50);
		jbtnBefore.setBounds(150,500,100,50);
		
		
		add(orderTitle);
		add(scroll);
		add(jlblAmountPrice);
		add(jlblChoosePayMethod);
		add(jbtnCard);
		add(jbtnPay);
		add(jbtnBefore);
		
		
		
		setSize(400,600);
		setLocation(800,300);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}//OrderPayDesign
	
	

	public JLabel getJlblOrderResult2() {
		return jlblAmountPrice;
	}


	public JButton getJbtnCard() {
		return jbtnCard;
	}

	public JButton getJbtnPay() {
		return jbtnPay;
	}

	public JButton getJbtnBefore() {
		return jbtnBefore;
	}

	public static void main(String[] args) {
		new OrderPayDesign();
	}

}//class
