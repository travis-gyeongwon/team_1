package kiosk.admin.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import kiosk.admin.event.CalendarEvent;

public class CalendarDesign extends JDialog {
	private JTextField selectTextField;
	
	private DefaultComboBoxModel<String> dcmYear;
	private JComboBox<String> jcbYear;
	private DefaultComboBoxModel<String> dcmMonth;
	private JComboBox<String> jcbMonth;
	
	private JButton[][] jbtnDay;
	
	public CalendarDesign(SalesReportDesign srd, JTextField selectTextField) {
		super(srd,true);
		this.selectTextField = selectTextField;
		
		dcmYear = new DefaultComboBoxModel<String>(setYear());
		dcmYear.setSelectedItem("2025");
		jcbYear = new JComboBox<String>(dcmYear);
		JLabel jlYear = new JLabel("년");
		dcmMonth = new DefaultComboBoxModel<String>(setMonth());
		dcmMonth.setSelectedItem(Calendar.getInstance().get(Calendar.MONTH)+1);
		jcbMonth = new JComboBox<String>(dcmMonth);
		JLabel jlMonth = new JLabel("월");
		
		JPanel jpNorth = new JPanel();
		jpNorth.add(jcbYear);
		jpNorth.add(jlYear);
		jpNorth.add(jcbMonth);
		jpNorth.add(jlMonth);
		
		CalendarEvent ce = new CalendarEvent(this);
		jcbYear.addActionListener(ce);
		jcbMonth.addActionListener(ce);
		
		jbtnDay = new JButton[6][7];
		setDay(ce);
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(new GridLayout(7,7));
		
		Font font = new Font("맑은 고딕", Font.BOLD, 15);
		String dayText ="일,월,화,수,목,금,토,일";
		String[] dayTextArr = dayText.split(",");
		JLabel[] jlDayText = new JLabel[7]; 
		for(int i = 0; i < 7; i++) {
			jlDayText[i] = new JLabel();
			jlDayText[i].setText(dayTextArr[i]);
			jlDayText[i].setFont(font);
			jlDayText[i].setHorizontalAlignment(SwingConstants.CENTER);
			jpCenter.add(jlDayText[i]);
		}
		for(int i = 0; i < jbtnDay.length; i++) {
			for(int j = 0; j < jbtnDay[0].length; j++) {
				jpCenter.add(jbtnDay[i][j]);
			}
		}
		
		add("North", jpNorth);
		add("Center", jpCenter);
		
		setBounds(100,100,400,300);
		setVisible(true);
	}
	
	private String[] setYear() {
		//최근 5년까지의 기록 보여주기
		String[] yearArr = new String[5];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		for(int i = yearArr.length -1 ; i > -1; i--) {
			yearArr[i] = String.valueOf(year);
			year--;
		}
		return yearArr;
	}
	private String[] setMonth() {
		String[] monthArr = new String[12];
		for(int i = 0 ; i < 12; i++) {
			monthArr[i] = String.valueOf(i+1);
		}
		return monthArr;
	}
	
	private void setDay(CalendarEvent ce) {		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		int weekCnt = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		weekCnt = 0;
		int dayCount = 0;
		int startDayofWeek = cal.get(Calendar.DAY_OF_WEEK);
		for(int i = 1; i < startDayofWeek ; i++) {
			jbtnDay[weekCnt][dayCount] = new JButton();
			jbtnDay[weekCnt][dayCount].addActionListener(ce);
			jbtnDay[weekCnt][dayCount++].setEnabled(false);
		}
		
		for(int day = 1; ; day++) {
			cal.set(Calendar.DAY_OF_MONTH, day);
			//값이 다를 경우 다음 달로 넘어간 것이기 때문에 공백으로 넣어주면 됨
			if(day != cal.get(Calendar.DAY_OF_MONTH)) {
				//다음 달의 1일의 요일을 받아서 토요일까지 공백으로 넣어주기
				for(int blank = cal.get(Calendar.DAY_OF_WEEK); blank < Calendar.SATURDAY + 1; blank++) {
					jbtnDay[weekCnt][dayCount] = new JButton();
					jbtnDay[weekCnt][dayCount].addActionListener(ce);
					jbtnDay[weekCnt][dayCount++].setEnabled(false);
				}
				if(weekCnt == 4) {
					weekCnt++;
					dayCount = 0;
					for(int i = 0; i < 7 ; i++) {
						jbtnDay[weekCnt][dayCount] = new JButton();
						jbtnDay[weekCnt][dayCount].addActionListener(ce);
						jbtnDay[weekCnt][dayCount++].setEnabled(false);
					}
				}
				break;
			}
			jbtnDay[weekCnt][dayCount] = new JButton(String.valueOf(day));
			jbtnDay[weekCnt][dayCount++].addActionListener(ce);
			
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				weekCnt++;
				dayCount = 0;
			}
		}
	}

	public DefaultComboBoxModel<String> getDcmYear() {
		return dcmYear;
	}

	public JComboBox<String> getJcbYear() {
		return jcbYear;
	}

	public DefaultComboBoxModel<String> getDcmMonth() {
		return dcmMonth;
	}

	public JComboBox<String> getJcbMonth() {
		return jcbMonth;
	}

	public JButton[][] getJbtnDay() {
		return jbtnDay;
	}

	public JTextField getSelectTextField() {
		return selectTextField;
	}
	
}
