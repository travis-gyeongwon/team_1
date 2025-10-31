package kiosk.user.view;

import java.awt.Color;
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
import kiosk.user.dto.OrderProductDTO;
import kiosk.user.service.OrderPayService;

public class OrderPayDesign extends JDialog{

	DefaultTableModel dtm;
	JTable jtOrderList;
	public JLabel jlblAmountPrice;
	public JButton jbtnCard;
	public JButton jbtnPay;
	OrderPayService ops;
	private OrderDesign od;
	
	private static final Color COLOR_BG = Color.WHITE;
	
	
	public OrderDesign getOd() {
		return od;
	}


	public OrderPayDesign(OrderDesign od) {
		super(od,"주문내역",true);
		this.od=od;
		
		OrderProductDTO oDTO=new OrderProductDTO();
		
     	
	    ops=new OrderPayService();
	    String[]tableColumn={"메뉴명","수량","금액"};
	    
	    dtm=new DefaultTableModel(tableColumn, 0);	
		jtOrderList=new JTable(dtm);
        jtOrderList.setEnabled(false);
		
		
		List<OrderPayDTO>orderDetailList=ops.showOrderDetail();
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
	
        int[]total=ops.amountPriceTotal();
		String quantity=String.valueOf(total[0]);
		String price=String.valueOf(total[1]);
		
		String amountPrice="총 수량: "+quantity+"개 총 금액: "+price+"원";
		jlblAmountPrice=new JLabel(amountPrice);
		
		Font font=new Font("맑은고딕",Font.BOLD,30);
		
		JLabel orderTitle=new JLabel("주문내역");
		JLabel jlblChoosePayMethod=new JLabel("결제방식을 선택해주세요!");
		
		jbtnCard=new JButton("카드");
		jbtnPay=new JButton("페이");
		
		OrderPayEvent ope=new OrderPayEvent(this);
		jbtnCard.addActionListener(ope);
		jbtnPay.addActionListener(ope);
		addWindowListener(ope);
		
		setLayout(null);
		orderTitle.setFont(font);
		jlblAmountPrice.setFont(font);
		orderTitle.setBounds(300,10,200,50);
		scroll.setBounds(120,100,450,500);
		jlblAmountPrice.setBounds(150,600,500,40);
		jlblChoosePayMethod.setBounds(240,650,300,40);
		jlblChoosePayMethod.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		jbtnCard.setBounds(140,720,100,50);
		jbtnCard.setBackground(Color.black);
		jbtnCard.setForeground(COLOR_BG);
		jbtnPay.setBounds(440,720,100,50);
		jbtnPay.setBackground(Color.black);
		jbtnPay.setForeground(COLOR_BG);
		
		
		add(orderTitle);
		add(scroll);
		add(jlblAmountPrice);
		add(jlblChoosePayMethod);
		add(jbtnCard);
		add(jbtnPay);
		
		
		
		setSize(700, 1000); 
		setLocationRelativeTo(null);
		getContentPane().setBackground(COLOR_BG);
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



}//class
