package com.nineplus.pharmacy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicineExport")
public class MedicineExport {

	private long id;
	private String medicineName;
	// Công ty sản xuất
	private String medicineCompany;
	private Date exportDate;
	private long amount;
	public MedicineExport() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "medicine_name", nullable = false)
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	
	@Column(name = "medicine_company", nullable = false)
	public String getMedicineCompany() {
		return medicineCompany;
	}
	public void setMedicineCompany(String medicineCompany) {
		this.medicineCompany = medicineCompany;
	}
	
	@Column(name = "export_date", nullable = false)
	public Date getExportDate() {
		return exportDate;
	}
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	
	@Column(name = "amount", nullable = false)
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "MedicineExport [id=" + id + ", medicineName=" + medicineName + ", medicineCompany=" + medicineCompany
				+ ", exportDate=" + exportDate + ", amount=" + amount + "]";
	}
	
	
}
