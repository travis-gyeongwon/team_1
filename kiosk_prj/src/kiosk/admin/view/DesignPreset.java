package kiosk.admin.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DesignPreset {
	
	public void setButtonDesign(JButton btn, int size) {
		Font font = new Font("맑은 고딕", Font.BOLD, size);
		Color btnColor = Color.decode("#C6B2A2");
		Color fontColor = Color.decode("#333333");
		btn.setBackground(btnColor);
		btn.setForeground(fontColor);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setFont(font);
	}
	
	public void setButtonDesign(JButton btn, int size, String fontColorStr, String btnColorStr){
		String fontStr="#C6B2A2";
		String btnStr="#333333";
		if(fontColorStr!=null) fontStr = fontColorStr;
		if(btnColorStr!=null) btnStr = btnColorStr;
		
		Font font = new Font("맑은 고딕", Font.BOLD, size);
		Color btnColor = Color.decode(fontStr);
		Color fontColor = Color.decode(btnStr);
		btn.setBackground(btnColor);
		btn.setForeground(fontColor);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setFont(font);
	}
	
	public void setTextPlain(JComponent jcom, int size) {
		Font font = new Font("맑은 고딕", Font.PLAIN, size);
		jcom.setFont(font);
	}

	public void setTextBold(JComponent jcom, int size) {
		Font font = new Font("맑은 고딕", Font.BOLD, size);
		jcom.setFont(font);
	}
	
	public void setColor(JComponent jcom, String color) {
		Color setColor = Color.decode(color);
		jcom.setBackground(setColor);
	}
	
	public void setPannelColor(Container con) {
		Color color = Color.decode("#FFFFFF");
		con.setBackground(color);
	}
}
