package kiosk.admin.view;

import kiosk.admin.dto.MenuListSelectDTO;
import kiosk.admin.dto.MenuListUpdateDTO;
import kiosk.admin.event.MenuListEvent;
import kiosk.admin.service.AdminMenuListService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
// import java.awt.Frame; // Frame 대신 AdminMainDesign 사용
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

// AdminMainDesign import 추가 (라벨 접근 위해)
import kiosk.admin.view.AdminMainDesign;

public class MenuListDesign extends JDialog {

    private DefaultTableModel dtmCoffee, dtmNonCoffee;
    private JTable jtCoffee, jtNonCoffee;
    private JButton jbtnConfirm = new JButton("확인");

    private final AdminMenuListService service = new AdminMenuListService();
    // private Frame parentFrame; // Frame 대신 AdminMainDesign 사용
    private AdminMainDesign amd; // AdminMainDesign 참조 변수

    // 생성자에서 AdminMainDesign 객체(amd) 받기
    public MenuListDesign(AdminMainDesign parentAmd){
        super(parentAmd, "메뉴 목록", true); // 부모 설정, 제목 설정, Modal 설정
        // this.parentFrame = parentAmd; // Frame 대신 AdminMainDesign 사용
        this.amd = parentAmd; // 전달받은 AdminMainDesign 객체 저장

        setLayout(null);

        String[] cols = {"메뉴명","옵션","사이즈","가격","상태"};

        dtmCoffee = new DefaultTableModel(cols, 0){ public boolean isCellEditable(int r,int c){ return false; } };
        dtmNonCoffee = new DefaultTableModel(cols, 0){ public boolean isCellEditable(int r,int c){ return false; } };

        jtCoffee = new JTable(dtmCoffee);
        jtNonCoffee = new JTable(dtmNonCoffee);

        JLabel jlbCoffee = new JLabel("커피");
        jlbCoffee.setBounds(20, 20, 100, 25);
        JScrollPane jspCoffee = new JScrollPane(jtCoffee);
        jspCoffee.setBounds(20, 45, 540, 180);

        JLabel jlbNonCoffee = new JLabel("논커피");
        jlbNonCoffee.setBounds(20, 235, 100, 25);
        JScrollPane jspNon = new JScrollPane(jtNonCoffee);
        jspNon.setBounds(20, 260, 540, 180);

        jbtnConfirm.setBounds(240, 460, 100, 32);
        add(jlbCoffee); add(jspCoffee);
        add(jlbNonCoffee); add(jspNon);
        add(jbtnConfirm);

        loadTables();

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 더블클릭 시에만 작동
                if(e.getClickCount() == 2){
                    // 영업 상태 확인
                    String currentStatusText = amd.getJlStatus().getText();
                    boolean isOpen = currentStatusText != null && currentStatusText.contains("영업 중");

                    // [수정!] 영업 중일 경우 수정 차단
                    if (isOpen) { // isOpen이 true일 때 (영업 중)
                        JOptionPane.showMessageDialog(MenuListDesign.this,
                            "영업 중에는 메뉴 수정이 불가능합니다.\n영업 종료 후에 시도해주세요.",
                            "수정 불가", JOptionPane.WARNING_MESSAGE);
                        return; // 수정 창 열기 중단
                    }

                    // 영업 종료 상태일 때만 아래 로직 실행
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    // 첫 번째 열(메뉴명)을 더블클릭했고, 유효한 행일 경우
                    if(row != -1 && target.getSelectedColumn() == 0) {
                        String menuName = target.getValueAt(row, 0).toString();

                        // DB에서 메뉴 상세 정보 조회
                        MenuListUpdateDTO detailDto = service.getMenuDetails(menuName);

                        if (detailDto == null || detailDto.getMenuName() == null) {
                            JOptionPane.showMessageDialog(MenuListDesign.this,
                                "[" + menuName + "] 메뉴의 상세 정보를 불러오는 데 실패했습니다.",
                                "오류", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // MenuUpdateDesign 생성 시 부모 프레임(amd) 전달
                        MenuUpdateDesign updateDialog = new MenuUpdateDesign(amd, detailDto);
                        updateDialog.setVisible(true); // 수정 창이 닫힐 때까지 대기

                        loadTables(); // 수정 창이 닫힌 후, 목록 새로고침
                    }
                }
            }
        };

        jtCoffee.addMouseListener(mouseAdapter);
        jtNonCoffee.addMouseListener(mouseAdapter);

        // 확인 버튼 이벤트 연결
        new MenuListEvent(this);

        setSize(600, 540);
        setLocationRelativeTo(amd); // 부모 창 기준으로 중앙 정렬
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * 테이블 데이터를 다시 로드하는 메서드
     */
    private void loadTables(){
        dtmCoffee.setRowCount(0);
        dtmNonCoffee.setRowCount(0);

        List<MenuListSelectDTO> coffeeList = service.searchAllMenuList(1);
        for (MenuListSelectDTO d : coffeeList) {
            String opt = String.join("/", d.getTempOption() != null ? d.getTempOption() : new String[]{""});
            String size = String.join(" / ", d.getSizeOption() != null ? d.getSizeOption() : new String[]{""});
            dtmCoffee.addRow(new Object[]{d.getMenuName(), opt, size, d.getMenuPrice(), d.getOrderStatus()});
        }

        List<MenuListSelectDTO> nonCoffeeList = service.searchAllMenuList(2);
        for (MenuListSelectDTO d : nonCoffeeList) {
            String opt = String.join("/", d.getTempOption() != null ? d.getTempOption() : new String[]{""});
            String size = String.join(" / ", d.getSizeOption() != null ? d.getSizeOption() : new String[]{""});
            dtmNonCoffee.addRow(new Object[]{d.getMenuName(), opt, size, d.getMenuPrice(), d.getOrderStatus()});
        }
    }

    public JButton getJbtnConfirm(){ return jbtnConfirm; }

    // 부모 프레임을 반환하는 getter (필요시 사용)
    public AdminMainDesign getParentFrame() { return amd; }
}

