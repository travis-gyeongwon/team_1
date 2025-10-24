package kiosk.admin.view;

import kiosk.admin.dto.MenuListSelectDTO;
import kiosk.admin.dto.MenuListUpdateDTO;
import kiosk.admin.event.MenuListEvent;
import kiosk.admin.service.AdminMenuListService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MenuListDesign extends JDialog implements MouseListener {

    private DefaultTableModel dtmCoffee, dtmNonCoffee;
    private JTable jtCoffee, jtNonCoffee;
    private JButton jbtnConfirm = new JButton("확인");

    private final AdminMenuListService service = new AdminMenuListService();

    public MenuListDesign(){
        setTitle("메뉴 목록");
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

        jtCoffee.addMouseListener(this);
        jtNonCoffee.addMouseListener(this);

        new MenuListEvent(this);

        setSize(600, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
       
        setVisible(true);
    }

    private void loadTables(){
        dtmCoffee.setRowCount(0);
        dtmNonCoffee.setRowCount(0);

        List<MenuListSelectDTO> coffeeList = service.searchAllMenuList(1);
        for (MenuListSelectDTO d : coffeeList) {
            String opt = String.join("/", d.getTempOption());
            String size = String.join(" / ", d.getSizeOption());
            dtmCoffee.addRow(new Object[]{d.getMenuName(), opt, size, d.getMenuPrice(), d.getOrderStatus()});
        }

        List<MenuListSelectDTO> nonCoffeeList = service.searchAllMenuList(2);
        for (MenuListSelectDTO d : nonCoffeeList) {
            String opt = String.join("/", d.getTempOption());
            String size = String.join(" / ", d.getSizeOption());
            dtmNonCoffee.addRow(new Object[]{d.getMenuName(), opt, size, d.getMenuPrice(), d.getOrderStatus()});
        }
    }

    @Override public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 1){
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            int col = target.getSelectedColumn();

            if(col == 0 && row >= 0){
                String menuName = target.getValueAt(row,0).toString();
                int sel = JOptionPane.showConfirmDialog(this,
                        "'" + menuName + "' 메뉴를 수정하시겠습니까?", "메뉴 수정", JOptionPane.YES_NO_OPTION);

                if(sel == JOptionPane.YES_OPTION){
                    MenuListUpdateDTO detailDto = service.getMenuDetails(menuName);
                    
                    if (detailDto.getMenuName() == null) {
                        JOptionPane.showMessageDialog(this, "메뉴 상세 정보를 불러오는 데 실패했습니다.");
                        return;
                    }

                    MenuUpdateDesign updateDialog = new MenuUpdateDesign(detailDto);
                    updateDialog.setModal(true);
                    updateDialog.setVisible(true);

                    loadTables();
                }
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public JButton getJbtnConfirm(){ return jbtnConfirm; }
}