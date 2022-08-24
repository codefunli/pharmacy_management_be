package com.nineplus.pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReportInput {
	private Long id;
	private String medicineName;
	// tồn đầu kỳ 
	private Long inStock;
	private Long importQuantity;
	private Long exportQuantity;
	private Long currentQuantity;
	
}
