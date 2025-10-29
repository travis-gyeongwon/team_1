package kiosk.user.view; // 패키지 이름 확인

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Collections; // ❗️ Collections import 추가
import java.util.List;    // ❗️ List import 추가

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

// ❗️ 수정된 Event 사용
import kiosk.user.event.OptionEvent;
// ❗️ 수정된 DTO 사용
import kiosk.user.dto.OrderMenuDTO;

public class OptionDesign extends JDialog {

	private JLabel jlblMenu;
	private JLabel jlblTemp;
	private JButton jbtnHot;
	private JButton jbtnIce;
	private JLabel jlblSize;
	private JButton jbtnMedium;
	private JButton jbtnLarge;
	private JLabel jlblOption;
	private JButton jbtnNormal;
	private JButton jbtnMild;
	private JButton jbtnShot;
	private JLabel jlblCount;
	private JButton jbtnMinus;
	private JLabel jlblCounter;
	private JButton jbtnPlus;
	private JLabel jlblTotal;
	private JLabel jlblTprice;
	private JButton jbtnCheck;

	public OptionDesign(OrderDesign parent, OrderMenuDTO menuDto) {
		super(parent, "상품 세부 정보", true);

		// --- ❗️ 허용된 옵션 코드 목록 가져오기 (null 체크 포함) ---
		List<Integer> allowedTemps = menuDto.getAllowedTempCodes() != null ? menuDto.getAllowedTempCodes() : Collections.emptyList();
		List<Integer> allowedSizes = menuDto.getAllowedSizeCodes() != null ? menuDto.getAllowedSizeCodes() : Collections.emptyList();
		List<Integer> allowedShots = menuDto.getAllowedShotCodes() != null ? menuDto.getAllowedShotCodes() : Collections.emptyList();

		// --- 컴포넌트 생성 ---
		jlblMenu = new JLabel("상품명 : " + menuDto.getMenuName());
		jlblTemp = new JLabel("핫,아이스");
		jbtnHot = new JButton("              HOT              ");
		jbtnIce = new JButton("              ICE              ");
		jlblSize = new JLabel("사이즈");
		jbtnMedium = new JButton("          Regular           "); // 버튼 텍스트는 Regular
		jbtnLarge = new JButton("   Large(+500원)    ");
		jlblOption = new JLabel("추가옵션");
		jbtnNormal = new JButton("  기본  ");
		jbtnMild = new JButton(" 연하게 ");
		jbtnShot = new JButton(" 샷추가(+500원) ");
		jlblCount = new JLabel("개수");
		jbtnMinus = new JButton("-");
		jlblCounter = new JLabel("1개");
		jbtnPlus = new JButton("+");
		jlblTotal = new JLabel("총액");
		jlblTprice = new JLabel(menuDto.getPrice() + "원");
		jbtnCheck = new JButton("확인");

		// --- ❗️ 버튼 활성화/비활성화 처리 (DB 코드 기준) ---
		jbtnHot.setEnabled(allowedTemps.contains(1));   // TEMP 코드 1 (Hot)
		jbtnIce.setEnabled(allowedTemps.contains(2));   // TEMP 코드 2 (Ice)
		jbtnMedium.setEnabled(allowedSizes.contains(1)); // SIZE 코드 1 (Regular/Medium)
		jbtnLarge.setEnabled(allowedSizes.contains(2)); // SIZE 코드 2 (Large)
		jbtnNormal.setEnabled(allowedShots.contains(1)); // SHOT 코드 1 (기본)
		jbtnMild.setEnabled(allowedShots.contains(2));   // SHOT 코드 2 (연하게)
		jbtnShot.setEnabled(allowedShots.contains(3));   // SHOT 코드 3 (샷 추가)
		

		// --- ❗️ 유효한 기본 선택 옵션 결정 ---
		String defaultTemp = allowedTemps.contains(2) ? "ICE" : (allowedTemps.contains(1) ? "HOT" : (allowedTemps.isEmpty() ? null : (allowedTemps.get(0) == 1 ? "HOT" : "ICE")));
		String defaultSize = allowedSizes.contains(1) ? "Medium" : (allowedSizes.contains(2) ? "Large" : (allowedSizes.isEmpty() ? null : (allowedSizes.get(0) == 1 ? "Medium" : "Large")));
		String defaultShot = allowedShots.contains(1) ? "기본" : (allowedShots.contains(2) ? "연하게" : (allowedShots.contains(3) ? "샷추가" : (allowedShots.isEmpty() ? null : (allowedShots.get(0) == 1 ? "기본" : (allowedShots.get(0) == 2 ? "연하게" : "샷추가")))));

		// --- 레이아웃 설정 ---
		Dimension labelSize = new Dimension(80, 30);
		jlblTemp.setPreferredSize(labelSize);
		jlblSize.setPreferredSize(labelSize);
		jlblOption.setPreferredSize(labelSize);
		jlblCount.setPreferredSize(labelSize);
		jlblTotal.setPreferredSize(labelSize);
		jlblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		jlblTemp.setHorizontalAlignment(SwingConstants.CENTER);
		jlblSize.setHorizontalAlignment(SwingConstants.CENTER);
		jlblOption.setHorizontalAlignment(SwingConstants.CENTER);
		jlblCount.setHorizontalAlignment(SwingConstants.CENTER);
		jlblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		jlblCounter.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel jpMenu = new JPanel(new FlowLayout(FlowLayout.CENTER)); jpMenu.add(jlblMenu);
		JPanel jpTemp = new JPanel(new FlowLayout(FlowLayout.LEFT)); jpTemp.add(jlblTemp); jpTemp.add(jbtnHot); jpTemp.add(jbtnIce);
		JPanel jpSize = new JPanel(new FlowLayout(FlowLayout.LEFT)); jpSize.add(jlblSize); jpSize.add(jbtnMedium); jpSize.add(jbtnLarge);
		JPanel jpOption = new JPanel(new FlowLayout(FlowLayout.LEFT)); jpOption.add(jlblOption); jpOption.add(jbtnNormal); jpOption.add(jbtnMild); jpOption.add(jbtnShot);
		JPanel jpCount = new JPanel(new FlowLayout(FlowLayout.LEFT)); jpCount.add(jlblCount); jpCount.add(jbtnMinus); jpCount.add(jlblCounter); jpCount.add(jbtnPlus);
		JPanel jpTotal = new JPanel(new FlowLayout(FlowLayout.LEFT)); jpTotal.add(jlblTotal); jpTotal.add(jlblTprice); jpTotal.add(jbtnCheck);

		JPanel mainPanel = new JPanel(new GridLayout(6, 1));
		mainPanel.setBorder(new EmptyBorder( 10, 10, 10, 10));
		mainPanel.add(jpMenu); mainPanel.add(jpTemp); mainPanel.add(jpSize);
		mainPanel.add(jpOption); mainPanel.add(jpCount); mainPanel.add(jpTotal);
		add(mainPanel);

		// ❗️ OptionEvent 생성 시 결정된 기본 옵션 전달
		new OptionEvent(this, parent, menuDto, defaultTemp, defaultSize, defaultShot);

		// --- 다이얼로그 설정 ---
		pack();
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	// --- Getters ---
	// (보내주신 코드와 동일)
	public JLabel getJlblMenu() { return jlblMenu; }
	public JButton getJbtnHot() { return jbtnHot; }
	public JButton getJbtnIce() { return jbtnIce; }
	public JButton getJbtnMedium() { return jbtnMedium; }
	public JButton getJbtnLarge() { return jbtnLarge; }
	public JButton getJbtnNormal() { return jbtnNormal; }
	public JButton getJbtnMild() { return jbtnMild; }
	public JButton getJbtnShot() { return jbtnShot; }
	public JButton getJbtnMinus() { return jbtnMinus; }
	public JLabel getJlblCounter() { return jlblCounter; }
	public JButton getJbtnPlus() { return jbtnPlus; }
	public JLabel getJlblTprice() { return jlblTprice; }
	public JButton getJbtnCheck() { return jbtnCheck; }

} // class 끝