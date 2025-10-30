package kiosk.admin.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kiosk.admin.service.AdminLoginService;
import kiosk.admin.view.AdminLoginDesign;
import kiosk.admin.view.AdminMainDesign;

public class AdminLoginEvent extends WindowAdapter implements ActionListener {
	private AdminLoginDesign ald;
	
	public AdminLoginEvent(AdminLoginDesign ald) {
		this.ald = ald;
	}

	private void confirmLogin() {
		String id = ald.getJtfId().getText().trim();
		String pw = new String(ald.getJtfPass().getPassword()).trim();
		if(id.isEmpty() || pw.isEmpty()) {
			ald.getJlError().setText("아이디 또는 비밀번호를 입력해주세요.");
			if(id.isEmpty())
				ald.getJtfId().requestFocus();
			return;
		}
		ald.getJlError().setText("");
		AdminLoginService als = new AdminLoginService();
		if(als.searchLogin(id, pw) != null) {
			new AdminMainDesign(als.getlDTO());
			ald.dispose();
		}else {
			JOptionPane.showMessageDialog(ald, "아이디/비밀번호를 확인해주세요");
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == ald.getJtfId()) {
			ald.getJtfPass().requestFocus();
		}
		if(ae.getSource() == ald.getJtfPass()) {
			confirmLogin();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		ald.dispose();
	}
	
	
}
