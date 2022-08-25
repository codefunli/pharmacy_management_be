package com.nineplus.pharmacy.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelRow;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.RowColumn;
import com.gembox.spreadsheet.SpreadsheetInfo;
import com.nineplus.pharmacy.exception.ResourceNotFoundException;
import com.nineplus.pharmacy.model.ReportInput;

@Service
public class ExportReportService {
	
	@Autowired
	MedicineService medicineSerivice;
	
	private static final String EXTENSION = ".xlsx";	
	
	public File export(int month) throws ResourceNotFoundException, IOException {
		SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
		ExcelFile workbook;
		
		List<ReportInput> data = medicineSerivice.getReportData(month);
		
		try {
			workbook = ExcelFile.load("src\\main\\resources\\IC-Medical-Invoice-Template-10541.xlsx");
		} catch (IOException e) {
			throw new ResourceNotFoundException("File not found");
		}
        LocalDateTime startDate = LocalDateTime.now().plusDays(-30);
        LocalDateTime endDate = LocalDateTime.now();
        
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        // Find cells with placeholder text and set their values in header
        RowColumn rowColumnPosition;
        if ((rowColumnPosition = worksheet.getCells().findText("[REPORT ID]", true, true)) != null)
            worksheet.getCell(rowColumnPosition.getRow(), rowColumnPosition.getColumn()).setValue("nine12345");
        if ((rowColumnPosition = worksheet.getCells().findText("[FULL NAME]", true, true)) != null)
            worksheet.getCell(rowColumnPosition.getRow(), rowColumnPosition.getColumn()).setValue("Nguyen Van A");
        if ((rowColumnPosition = worksheet.getCells().findText("[REPORT DATE]", true, true)) != null)
            worksheet.getCell(rowColumnPosition.getRow(), rowColumnPosition.getColumn()).setValue(endDate);
        if ((rowColumnPosition = worksheet.getCells().findText("[START DATE]", true, true)) != null)
            worksheet.getCell(rowColumnPosition.getRow(), rowColumnPosition.getColumn()).setValue(startDate);
        if ((rowColumnPosition = worksheet.getCells().findText("[END DATE]", true, true)) != null)
            worksheet.getCell(rowColumnPosition.getRow(), rowColumnPosition.getColumn()).setValue(endDate);
        // Copy template row.
        int row = 11;
        worksheet.getRows().insertCopy(row , 11, worksheet.getRow(row));
        
        // Fill inserted rows with sample data.
        	for (int i = 10; i < data.size() + 10; i++) {
                ExcelRow currentRow = worksheet.getRow( i);
                for(int j = i- 10; j < data.size();) {
                	currentRow.getCell(3).setValue(j + 1);
                    currentRow.getCell(4).setValue(data.get(j).getMedicineName());
                    currentRow.getCell(5).setValue(data.get(j).getTotalImport());
                    currentRow.getCell(6).setValue(data.get(j).getTotalExport());
                    currentRow.getCell(7).setValue(data.get(j).getAmountStartMonth());
                    currentRow.getCell(8).setValue(data.get(j).getAmountEndMonth());
                    break;
                }   
            }
        String fileName = "Report_"+ endDate.toEpochSecond(ZoneOffset.UTC) + EXTENSION;
		workbook.save(fileName);
		File file = new File(fileName);
		return file;
	}
	

}
