package kiosk.user.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kiosk.user.view.EndDesign;
import kiosk.user.view.PrintDecisionDesign;
import kiosk.user.view.PrintReceiptDesign;

public class PrintDecisionEvent extends WindowAdapter implements ActionListener {

	private PrintDecisionDesign ud;

	public PrintDecisionEvent(PrintDecisionDesign ud) {
		this.ud = ud;
	}// PrintDecisionEvent

	@Override
	public void windowClosing(WindowEvent e) {
		ud.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ud.getJbtnNp()) {
			ud.dispose();
			new EndDesign();
		}// end if
		
		if(e.getSource()==ud.getJbtnYp()) {
			ud.dispose();
			new PrintReceiptDesign();
		}// end if
	}// actionPerformed

}// class
