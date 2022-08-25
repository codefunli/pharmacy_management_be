package com.nineplus.pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportInput {
	private String medicineName;
	private Long totalImport;
	private Long totalExport;
	private Long amountStartMonth;
	private Long amountEndMonth;
	
}
