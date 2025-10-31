package kiosk.admin.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import kiosk.admin.event.AdminMainEvent;
import kiosk.admin.dto.LoginDTO;

public class AdminMainDesign extends JFrame {
	private LoginDTO lDTO;
	
	private JButton jbtnMenu; //메뉴 관리
	private JButton jbtnOrder; //~~ 관리
	private JButton jbtnInven; //~~ 관리
	private JButton jbtnSales; //정산 관리
	private JButton jbtnOpen; //영업 시작
	private JButton jbtnClose; //영업 종료
	
	private JLabel jlLastLoginDate;//마지막으로 로그인한 시간 표시
	private JLabel jlStatus; //영업 상태 표시
	
	public AdminMainDesign(LoginDTO lDTO) {
		super("관리자 화면");
		this.lDTO = lDTO;
		
		ImageIcon titleIcon = new ImageIcon(getClass().getResource("/kiosk/admin/images/coffee_icon_v2.png"));
		if(titleIcon.getImage() != null) {
			setIconImage(titleIcon.getImage());
		}
		
		jbtnMenu = new JButton("메뉴 관리");
		jbtnOrder = new JButton("주문 관리");
		jbtnInven = new JButton("재고 관리");
		jbtnSales = new JButton("정산 관리");
		jbtnOpen = new JButton("영업 시작");
		jbtnClose = new JButton("영업 종료");
		jlLastLoginDate = new JLabel("마지막 로그인 날짜 : "+lDTO.getLoginDate());
		jlStatus = new JLabel();
		

		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		JPanel jp = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//JLabel 공통
		gbc.ipady = 5; //JLabel의 크기 추가
		gbc.fill = GridBagConstraints.HORIZONTAL; //JLabel이 수평으로 꽉 차도록
		gbc.weightx = 1.0; //수평 공간 모두 사용
		gbc.weighty = 0.0; //수직은 0. 세로 공간을 차지하지 않고 상단에 고정시킴
		
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(15, 15, -15, 0);
		
		//마지막 로그인
		gbc.gridy = 0;
		jp.add(jlLastLoginDate, gbc);		
		
		//영업 상태
		gbc.gridy = 1;
		jp.add(jlStatus, gbc);	
		
		JPanel jpBlanck = new JPanel();
		JPanel jpBlanck2 = new JPanel();
		
		//세로 공간을 추가하여 상단에 배치하는 것과 구분함
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 0.5; //공간을 차지해야함
		gbc.fill = GridBagConstraints.VERTICAL;
		jp.add(jpBlanck, gbc);
		
		//JButton 공통
		gbc.insets = new Insets(30, 15, 0, 15);
		gbc.ipady = 30;
		
		gbc.gridwidth = 1;//위에 수정해준 부분 초기화
		gbc.weighty = 0.0; //버튼은 공간을 차지할 필요가 없음
		
		//버튼 3행 3열
		//오픈
		gbc.gridx = 0;
		gbc.gridy = 3;
		jp.add(jbtnOpen, gbc);
		
		//클로즈
		gbc.gridx = 1;
		gbc.gridy = 3;
		jp.add(jbtnClose, gbc);
		
		//메뉴 관리
		gbc.gridx = 0;
		gbc.gridy = 4;
		jp.add(jbtnMenu, gbc);
		
		//주문 관리
		gbc.gridx = 1;
		gbc.gridy = 4;
		jp.add(jbtnOrder, gbc);
		
		//재고 관리
		gbc.gridx = 0;
		gbc.gridy = 5;
		jp.add(jbtnInven, gbc);
		
		//정산 관리
		gbc.gridx = 1;
		gbc.gridy = 5;
		jp.add(jbtnSales, gbc);
		
		//빈 패널을 추가하여 위와 아래에 공간을 넣어 button이 가운데로 위치할 수 있도록 함
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weighty = 1.0; //공간을 차지해야함
		gbc.fill = GridBagConstraints.VERTICAL;
		jp.add(jpBlanck2, gbc);

		contentPane.add(jp, new GridBagConstraints());
		
		
		//디자인
		DesignPreset dp = new DesignPreset();
		dp.setButtonDesign(jbtnOpen, 15, "#964B00", "#FFFFFF");
		dp.setButtonDesign(jbtnClose, 15,"#964B00", "#FFFFFF");
		dp.setButtonDesign(jbtnMenu, 15);
		dp.setButtonDesign(jbtnInven, 15);
		dp.setButtonDesign(jbtnOrder, 15);
		dp.setButtonDesign(jbtnSales, 15);
		dp.setTextPlain(jlLastLoginDate, 15);
		dp.setTextPlain(jlStatus, 15);
				
		dp.setPannelColor(contentPane);
		dp.setPannelColor(jp);
		dp.setPannelColor(jpBlanck);
		dp.setPannelColor(jpBlanck2);
		
		
		AdminMainEvent ame = new AdminMainEvent(this);
		jbtnMenu.addActionListener(ame);
		jbtnOrder.addActionListener(ame);
		jbtnInven.addActionListener(ame);
		jbtnSales.addActionListener(ame);
		jbtnOpen.addActionListener(ame);
		jbtnClose.addActionListener(ame);
		addWindowListener(ame);
		
		setSize(380, 440);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//기본적으로 창이 닫히지 않도록 설정
		setVisible(true);
	}

	public JButton getJbtnMenu() {
		return jbtnMenu;
	}

	public JButton getJbtnOrder() {
		return jbtnOrder;
	}

	public JButton getJbtnInven() {
		return jbtnInven;
	}

	public JButton getJbtnSales() {
		return jbtnSales;
	}

	public JButton getJbtnOpen() {
		return jbtnOpen;
	}

	public JButton getJbtnClose() {
		return jbtnClose;
	}

	public JLabel getJlLastLoginDate() {
		return jlLastLoginDate;
	}

	public JLabel getJlStatus() {
		return jlStatus;
	}

	public LoginDTO getlDTO() {
		return lDTO;
	}
	
}
