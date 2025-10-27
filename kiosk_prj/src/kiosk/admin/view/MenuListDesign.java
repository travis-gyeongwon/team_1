package kiosk.admin.view;

import kiosk.admin.dto.MenuListSelectDTO;
import kiosk.admin.dto.MenuListUpdateDTO;
import kiosk.admin.event.MenuListEvent;
import kiosk.admin.service.AdminMenuListService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame; // Frame import 추가
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuListDesign extends JDialog {

    private DefaultTableModel dtmCoffee, dtmNonCoffee;
    private JTable jtCoffee, jtNonCoffee;
    private JButton jbtnConfirm = new JButton("확인");

    private final AdminMenuListService service = new AdminMenuListService();
    private Frame parentFrame;

    public MenuListDesign(Frame parent){
        super(parent, "메뉴 목록", true);
        this.parentFrame = parent;
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
                if(e.getClickCount() == 2){ // 더블클릭 시
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    if(row != -1 && target.getSelectedColumn() == 0) {
                        String menuName = target.getValueAt(row, 0).toString();

                        // [수정!] 확인 창(JOptionPane) 다시 추가
                        int sel = JOptionPane.showConfirmDialog(MenuListDesign.this,
                                "'" + menuName + "' 메뉴를 수정하시겠습니까?", "메뉴 수정", JOptionPane.YES_NO_OPTION);

                        // 사용자가 '예(Yes)'를 선택했을 때만 수정 창 열기
                        if(sel == JOptionPane.YES_OPTION){
                            MenuListUpdateDTO detailDto = service.getMenuDetails(menuName);

                            if (detailDto == null || detailDto.getMenuName() == null) {
                                JOptionPane.showMessageDialog(MenuListDesign.this,
                                    "[" + menuName + "] 메뉴의 상세 정보를 불러오는 데 실패했습니다.",
                                    "오류", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            MenuUpdateDesign updateDialog = new MenuUpdateDesign(parentFrame, detailDto);
                            updateDialog.setVisible(true); // 수정 창이 닫힐 때까지 대기

                            // 수정 창이 닫힌 후, 목록 새로고침
                            loadTables();
                        } // end if (sel == YES_OPTION)
                    }
                }
            }
        };

        jtCoffee.addMouseListener(mouseAdapter);
        jtNonCoffee.addMouseListener(mouseAdapter);

        new MenuListEvent(this);

        setSize(600, 540);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

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
    public Frame getParentFrame() { return parentFrame; }
}

