package kiosk.admin.event;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kiosk.admin.dto.SalesReportDTO;
import kiosk.admin.service.SalesReportService;
import kiosk.admin.view.CalendarDesign;
import kiosk.admin.view.SalesReportDesign;

public class SalesReportEvent extends MouseAdapter implements ActionListener{
	private SalesReportDesign srd;
	
	public SalesReportEvent(SalesReportDesign srd) {
		this.srd = srd;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == srd.getJbtnSearch()) {
			searchResult();
		}
		if(ae.getSource() == srd.getJbtnSave()) {
			//파일 저장하기
			try {
				saveReport();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void searchResult() {
		//table 초기화
		srd.getDtmReport().setRowCount(0);
		srd.getJlTotalPrice().setText("0원");
		srd.getJbtnSave().setVisible(false);
		srd.getJlSavePath().setVisible(false);
		
		//textfield에서 입력된 내용 가져오기
		String startDate = srd.getJtfStartDate().getText();
		String endDate = srd.getJtfEndDate().getText();
		
		//기간 입력이 정확하게 되어있는지 확인
		//1. 값이 모두 입력되어 있는가
		if("".equals(startDate) || "".equals(endDate)) {
			JOptionPane.showMessageDialog(srd, "날짜를 입력해주세요");
			return;
		}
		//2. start보다 end의 날짜가 뒤인가
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date sd = sdf.parse(startDate);
			Date ed = sdf.parse(endDate);
			if(sd.after(ed)) {
				JOptionPane.showMessageDialog(srd, "날짜를 확인해주세요");
				return;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//1. date 형식으로 입력되었는가 -> 달력으로 선택해서 입력되도록 할 예정..
		//확인 완료되면 service로 넘김
		SalesReportService srs = new SalesReportService();
		SalesReportDTO[] srDTO = srs.searchReport(startDate, endDate);
		//검색된 값이 없을 경우 데이터 없음 안내
		if(srDTO == null) {
			JOptionPane.showMessageDialog(srd, "해당 날짜에는 판매 기록이 없습니다.");
			return;
		}
		
		String[] rowData = new String[3];
		int sumPrice = 0;
		for(SalesReportDTO srdto : srDTO) {
			rowData[0] = srdto.getName();
			rowData[1] = String.valueOf(srdto.getCount());
			rowData[2] = String.valueOf(srdto.getPrice());
			
			sumPrice += srdto.getPrice();
			
			srd.getDtmReport().addRow(rowData);
		}
		
		srd.getJlTotalPrice().setText(String.valueOf(sumPrice)+"원");
		srd.getJbtnSave().setVisible(true);
	}
	
	private void saveReport() throws IOException {
		String dirStr = "c:/dev/save";
		File fileDir = new File(dirStr);
		if(!fileDir.isDirectory()) {
			fileDir.mkdirs();//파일 없으면 생성
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = sdf.format(new Date())+"_"+String.valueOf(System.currentTimeMillis());
		String fileStr = dirStr+"/"+fileName+".csv";
		File file = new File(fileStr);
		
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		
		try {
			fos = new FileOutputStream(file);
			
			fos.write(0xEF);
		    fos.write(0xBB);
		    fos.write(0xBF);
		    
			osw = new OutputStreamWriter(fos);
			
			StringBuilder sb = new StringBuilder();
			sb.append("정산 기간 : ").append(srd.getJtfStartDate().getText()).append(" ~ ").append(srd.getJtfEndDate().getText()).append("\n\n");
			
			sb.append("메뉴명,수량,가격").append("\n");

			for(int i = 0; i < srd.getDtmReport().getRowCount(); i++) {
				sb.append(srd.getDtmReport().getValueAt(i, 0)).append(",")
				.append(srd.getDtmReport().getValueAt(i, 1)).append(",")
				.append(srd.getDtmReport().getValueAt(i, 2)).append("\n");
			}
			sb.append("총 금액,").append(srd.getJlTotalPrice().getText());
			
			osw.write(sb.toString());
			osw.flush();
			
			srd.getJlSavePath().setText(fileStr);
			srd.getJlSavePath().setVisible(true);
			srd.getJbtnSave().setVisible(false);
			
		}finally {
			if(fos != null) fos.close();
			if(osw != null) osw.close();
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource() == srd.getJtfStartDate()) {
			showCalendar(srd.getJtfStartDate());
		}
		if(me.getSource() == srd.getJtfEndDate()) {
			showCalendar(srd.getJtfEndDate());
		}
	}
	
	private void showCalendar(JTextField selectTextField) {
		new CalendarDesign(srd, selectTextField);
	}

}
