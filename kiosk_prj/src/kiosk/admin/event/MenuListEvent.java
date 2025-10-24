package kiosk.admin.event;

import kiosk.admin.view.MenuListDesign;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListEvent implements ActionListener {
    private final MenuListDesign mld;
    public MenuListEvent(MenuListDesign mld){
        this.mld = mld;
        this.mld.getJbtnConfirm().addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==mld.getJbtnConfirm()){
            mld.dispose();
        }
    }
}
