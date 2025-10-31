package kiosk.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kiosk.admin.event.SalesReportEvent;

public class SalesReportDesign extends JDialog {
	private JLabel jlTotalPrice;
	
	private JTextField jtfStartDate;
	private JTextField jtfEndDate;

	private JButton jbtnSearch;
	
	private JButton jbtnSave;
	private JLabel jlSavePath;
	
	private DefaultTableModel dtmReport;
	private JTable jtReport;
	
	public SalesReportDesign(AdminMainDesign amd) {
		super(amd, "정산 관리", true);
		
		Font font = new Font("맑은 고딕", Font.PLAIN, 14);
		Font saveFont = new Font("맑은 고딕", Font.PLAIN, 14);
		
		JLabel jlDateLabel = new JLabel("정산 기간");
		JLabel jlDateTilde = new JLabel("~");
		
		jtfStartDate = new JTextField(10);
		jtfEndDate = new JTextField(10);
		jtfStartDate.setEditable(false);
		jtfEndDate.setEditable(false);
		jbtnSearch = new JButton("검색");
		
		jlDateLabel.setFont(font);
		jlDateTilde.setFont(font);
		jtfStartDate.setFont(font);
		jtfEndDate.setFont(font);
		jbtnSearch.setFont(font);
		
		String[] columnNames= {"상품명","상품 판매 수량","상품 판매 금액"};
		dtmReport=new DefaultTableModel(columnNames,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtReport=new JTable( dtmReport );
		
		TableColumnModel tcm = jtReport.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(60);
		tcm.getColumn(1).setPreferredWidth(20);
		tcm.getColumn(2).setPreferredWidth(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tcm.getColumn(1).setCellRenderer(rightRenderer);
		tcm.getColumn(2).setCellRenderer(rightRenderer);
		JScrollPane jspReport = new JScrollPane(jtReport);
		jtReport.setFont(font);
		
		JLabel jlTotalPriceLabel = new JLabel("총 판매금액 :");
		jlTotalPrice = new JLabel("0원");
		jlTotalPriceLabel.setFont(font);
		jlTotalPrice.setFont(font);
		
		jbtnSave = new JButton("저장");
		jbtnSave.setVisible(false);
		jlSavePath = new JLabel();
		jlSavePath.setVisible(false);
		jlSavePath.setFont(saveFont);
		
		SalesReportEvent sre = new SalesReportEvent(this);
		jbtnSearch.addActionListener(sre);
		jbtnSave.addActionListener(sre);
		jtfStartDate.addMouseListener(sre);
		jtfEndDate.addMouseListener(sre);
		
		
		//위에 정산기간
		JPanel jpNorth = new JPanel();
		jpNorth.add(jlDateLabel);
		jpNorth.add(jtfStartDate);
		jpNorth.add(jlDateTilde);
		jpNorth.add(jtfEndDate);
		jpNorth.add(jbtnSearch);
		
		
		JPanel jpPrice = new JPanel();
		jpPrice.add(jlTotalPriceLabel);
		jpPrice.add(jlTotalPrice);
		
		JPanel jpSave = new JPanel();
		jpSave.setLayout(new BorderLayout());
		jpSave.add("Center",jlSavePath);
		jpSave.add("South",jbtnSave);
		
		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(new BorderLayout());
		jpSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
		jpSouth.add("West",jpPrice);
		jpSouth.add("East",jpSave);
		
		add("North", jpNorth);
		add("South", jpSouth);
		add("Center", jspReport);
		
		//디자인
		DesignPreset dp = new DesignPreset();
		dp.setButtonDesign(jbtnSave, 12);
		dp.setButtonDesign(jbtnSearch, 12);
		dp.setTextPlain(jlDateLabel, 15);
		dp.setTextPlain(jlDateTilde, 15);
		dp.setTextPlain(jlTotalPriceLabel, 15);
		dp.setTextPlain(jlSavePath, 15);
		dp.setTextPlain(jlTotalPrice, 15);
		dp.setTextPlain(jtfStartDate, 15);
		dp.setTextPlain(jtfEndDate, 15);
		dp.setTextPlain(jtReport, 13);
		
		dp.setPannelColor(jpSouth);
		dp.setPannelColor(jpPrice);
		dp.setPannelColor(jpSave);
		
		dp.setColor(jtReport, "#F5EFE7");
		dp.setColor(jpNorth, "#F5EFE7");
		dp.setColor(jtfStartDate, "#FFFFFF");
		dp.setColor(jtfEndDate, "#FFFFFF");
		
		Color myBackgroundColor = Color.decode("#F5EFE7");
		Color headerColor = Color.decode("#C6B2A2");
		jtReport.getTableHeader().setBackground(headerColor);
		jspReport.getViewport().setBackground(myBackgroundColor);
		
		
		
		setSize(500, 440);
		setLocationRelativeTo(amd);
		setResizable(false);
		setVisible(true);
	}

	public JLabel getJlTotalPrice() {
		return jlTotalPrice;
	}

	public JTextField getJtfStartDate() {
		return jtfStartDate;
	}

	public JTextField getJtfEndDate() {
		return jtfEndDate;
	}

	public JButton getJbtnSearch() {
		return jbtnSearch;
	}

	public DefaultTableModel getDtmReport() {
		return dtmReport;
	}

	public JTable getJtReport() {
		return jtReport;
	}

	public JButton getJbtnSave() {
		return jbtnSave;
	}

	public JLabel getJlSavePath() {
		return jlSavePath;
	}
	
}
