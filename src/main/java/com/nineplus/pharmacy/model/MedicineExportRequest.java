package com.nineplus.pharmacy.model;

import java.util.Date;

public class MedicineExportRequest {

	private long medicineId;
	private long amount;
	private Date exportDate;
	public MedicineExportRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(long medicineId) {
		this.medicineId = medicineId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public Date getExportDate() {
		return exportDate;
	}
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	
	
}
