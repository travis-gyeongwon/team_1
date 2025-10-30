package kiosk.admin.view;

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
		super(srd,"달력",true);
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
//		setDay(ce);
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
				jbtnDay[i][j] = new JButton();
				jbtnDay[i][j].addActionListener(ce);
				jpCenter.add(jbtnDay[i][j]);
			}
		}
		ce.setDayButton();
		
		add("North", jpNorth);
		add("Center", jpCenter);
		
//		setLocationRelativeTo(srd);
//		setSize(400,300);
		
		setSize(400,300);
		int x = srd.getLocation().x + srd.getSize().width/2 - getSize().width/2;
		int y = srd.getLocation().y + srd.getSize().height/2 - getSize().height/2;
		setLocation(x, y);
		
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
