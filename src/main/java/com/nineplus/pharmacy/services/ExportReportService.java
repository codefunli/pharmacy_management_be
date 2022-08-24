package com.nineplus.pharmacy.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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
	private static List<ReportInput> data = new ArrayList<>();
	static {
		data.add(new ReportInput((Long)1L, "Panadol", (Long)123L, (Long)25L, (Long)43L, (Long)234L));
		data.add(new ReportInput((Long)2L, "Astra", (Long)235L, (Long)65L, (Long)56L, (Long)784L));
		data.add(new ReportInput((Long)3L, "Modena", (Long)456L, (Long)72L, (Long)75L, (Long)354L));
		data.add(new ReportInput((Long)4L, "Flu", (Long)258L, (Long)92L, (Long)82L, (Long)124L));
		data.add(new ReportInput((Long)5L, "Dierhea", (Long)157L, (Long)45L, (Long)29L, (Long)167L));
	}
	
	public void export() throws ResourceNotFoundException {
		SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
		ExcelFile workbook;
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
                	currentRow.getCell(3).setValue(data.get(j).getId());
                    currentRow.getCell(4).setValue(data.get(j).getMedicineName());
                    currentRow.getCell(5).setValue(data.get(j).getInStock());
                    currentRow.getCell(6).setValue(data.get(j).getImportQuantity());
                    currentRow.getCell(7).setValue(data.get(j).getExportQuantity());
                    currentRow.getCell(8).setValue(data.get(j).getCurrentQuantity());
                    break;
                }   
            }
        try {
			workbook.save("Report "+ endDate.toEpochSecond(ZoneOffset.UTC) + ".xlsx");
			System.out.println("success");
		} catch (IOException e) {
			throw new ResourceNotFoundException("File can not save");
		}
	}

}
