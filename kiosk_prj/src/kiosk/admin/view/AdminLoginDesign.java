package kiosk.admin.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kiosk.admin.event.AdminLoginEvent;

public class AdminLoginDesign extends JFrame{
	private JLabel jlError;
	private JTextField jtfId;
	private JPasswordField jtfPass;
	private JButton jbLogin;
	
	public AdminLoginDesign() {
		super("관리자 로그인 화면");
		
		Font jlFont = new Font("맑은 고딕", Font.BOLD, 20);
		Font jtfFont = new Font("맑은 고딕", Font.PLAIN, 20);
		Font jbFont = new Font("맑은 고딕", Font.BOLD, 25);
		Font jlErrorFont = new Font("맑은 고딕", Font.BOLD, 15);
		
		JLabel jlId = new JLabel("아이디");
		JLabel jlPass = new JLabel("비밀번호");
		jlError = new JLabel("　");
		
		jtfId = new JTextField(10);
		jtfPass = new JPasswordField(10);
		
		jbLogin = new JButton("로그인");
		
		jlId.setFont(jlFont);
		jlPass.setFont(jlFont);
		jlError.setFont(jlErrorFont);
		jlError.setForeground(Color.red);

		jtfId.setFont(jtfFont);
		jtfPass.setFont(jtfFont);
		jbLogin.setFont(jbFont);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		JPanel jpLogin = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//컴포넌트 간의 여백 설정
		//Inserts(위, 왼쪽, 아래, 오른쪽)
		gbc.insets = new Insets(5, 5, 5, 5);
		
		//배치
		//행과 열로 위치 지정함
		//아이디
		gbc.gridx = 0;//열
		gbc.gridy = 0;//행
		gbc.anchor = GridBagConstraints.EAST;//오른쪽 정렬
		jpLogin.add(jlId, gbc);//gbc에서 설정한대로 JPanel에 넣기
		
		//텍스트 필드
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;//수평으로 꽉 채워주기
//		gbc.weightx = 1.0; //창 크기가 변할 때 수평으로 늘어날 가중치 설정이지만 크기 변경 시키지 않을 예정
		jpLogin.add(jtfId, gbc);
		
		//비밀번호
		gbc.gridx = 0;//열
		gbc.gridy = 1;//행
		gbc.anchor = GridBagConstraints.EAST;//오른쪽 정렬
		jpLogin.add(jlPass, gbc);//gbc에서 설정한대로 JPanel에 넣기
		
		//텍스트 필드
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		gbc.fill = GridBagConstraints.HORIZONTAL;//수평으로 꽉 채워주기
//		gbc.weightx = 1.0; //창 크기가 변할 때 수평으로 늘어날 가중치 설정이지만 크기 변경 시키지 않을 예정
		jpLogin.add(jtfPass, gbc);
		
		//로그인 버튼
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 2;//행 2개의 크기만큼 설정
		gbc.fill = GridBagConstraints.VERTICAL;//수직으로 꽉 채워주기. 안해주면 크기 작을 수 있음
//		gbc.weightx = 1.0; //창 크기가 변할 때 수평으로 늘어날 가중치 설정이지만 크기 변경 시키지 않을 예정
		jpLogin.add(jbLogin, gbc);
		
		//로그인 안내 label
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		jpLogin.add(jlError, gbc);
		
		//만든 패널을 중앙에 배치 시킴
		contentPane.add(jpLogin, new GridBagConstraints());
		
		//이벤트 설정
		AdminLoginEvent le = new AdminLoginEvent(this);
		jtfId.addActionListener(le);
		jtfPass.addActionListener(le);
		jbLogin.addActionListener(le);
		addWindowListener(le);
		
		setSize(535,240);
//		Dimension frameSize = getSize();
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation((screenSize.width-frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		setLocationRelativeTo(null);
		setResizable(false); 
		setVisible(true);
		
	}
	

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJtfPass() {
		return jtfPass;
	}

	public JButton getJbLogin() {
		return jbLogin;
	}

	public JLabel getJlError() {
		return jlError;
	}
}
