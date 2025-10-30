package kiosk.admin.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;

import kiosk.admin.view.CalendarDesign;

public class CalendarEvent implements ActionListener{
	//선택한 년/월에 따라 jbtn 수정
	//일자 선택하면 년/월/일을 super에 값 넣어주기
	//그리고 diagram 꺼지기
	
	private CalendarDesign cd;
	private int year;
	private int month;
	
	
	public CalendarEvent(CalendarDesign cd) {
		this.cd = cd;
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == cd.getJcbYear()) {
			year = Integer.parseInt(cd.getDcmYear().getElementAt(cd.getJcbYear().getSelectedIndex()));
			setDayButton();
			return;
		}
		if(ae.getSource() == cd.getJcbMonth()) {
			month = Integer.parseInt(cd.getDcmMonth().getElementAt(cd.getJcbMonth().getSelectedIndex()))-1;
			setDayButton();
			return;
		}
		
		//버튼 누르면 실행됨.
		setTextFieldDate((JButton)ae.getSource());
		cd.dispose();
		
	}
	
	public void setDayButton() {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		
		int weekCount = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		JButton[][] jbtnDay = cd.getJbtnDay();

		weekCount = 0;
		int dayCount = 0;
		int startDayofWeek = cal.get(Calendar.DAY_OF_WEEK);
		System.out.println(startDayofWeek);
		for(int i = 1; i < startDayofWeek ; i++) {
			jbtnDay[weekCount][dayCount].setText("");
			jbtnDay[weekCount][dayCount++].setEnabled(false);
		}
		for(int day = 1; ; day++) {
			cal.set(Calendar.DAY_OF_MONTH, day);
			//값이 다를 경우 다음 달로 넘어간 것이기 때문에 공백으로 넣어주면 됨
			if(day != cal.get(Calendar.DAY_OF_MONTH)) {
				//다음 달의 1일의 요일을 받아서 토요일까지 공백으로 넣어주기
				for(int blank = cal.get(Calendar.DAY_OF_WEEK); blank < Calendar.SATURDAY + 1; blank++) {
					jbtnDay[weekCount][dayCount].setText("");
					jbtnDay[weekCount][dayCount++].setEnabled(false);
				}
				if(weekCount == 4) {
					weekCount++;
					dayCount = 0;
					for(int i = 0; i < 7 ; i++) {
						jbtnDay[weekCount][dayCount].setText("");;
						jbtnDay[weekCount][dayCount++].setEnabled(false);
					}
				}
				break;
			}
			jbtnDay[weekCount][dayCount].setText(String.valueOf(day));
			jbtnDay[weekCount][dayCount++].setEnabled(true);
			
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				weekCount++;
				dayCount = 0;
			}
		}
	}
	
	private void setTextFieldDate(JButton button) {
		String dayStr = button.getText();
		int day = Integer.parseInt(dayStr);
		
		StringBuilder sbDate = new StringBuilder();
		sbDate.append(year).append("-").append(String.format("%02d", month+1)).append("-").append(String.format("%02d", day));

		
		cd.getSelectTextField().setText(sbDate.toString());
	}

}
