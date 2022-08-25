package com.nineplus.pharmacy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import lombok.Data;

@Entity
@NamedNativeQuery(
    name = "getReportData",
    query = "SELECT m.medicine_name, m.amount total_import, tx.amount total_export, m.amount amount_start_month, (m.amount - tx.amount) amount_end_month "
    		+ "FROM (SELECT m1.medicine_name, SUM(m1.amount) amount FROM medicine m1 WHERE MONTH(m1.import_date) = :month GROUP BY m1.medicine_name) as m "
    		+ "JOIN (SELECT m3.medicine_name, SUM(m3.amount) amount FROM medicine_export m3 WHERE MONTH(m3.export_date) = :month GROUP BY m3.medicine_name) as tx ON m.medicine_name = tx.medicine_name",
    resultSetMapping = "ReportInput"
)
@SqlResultSetMapping(
    name = "ReportInput",
    classes = @ConstructorResult(
        targetClass = ReportInput.class,
        columns = {
            @ColumnResult(name = "medicine_name", type = String.class),
            @ColumnResult(name = "total_import", type = Long.class),
            @ColumnResult(name = "total_export", type = Long.class),
            @ColumnResult(name = "amount_start_month", type = Long.class),
            @ColumnResult(name = "amount_end_month", type = Long.class)
        }
    )
)

@Data

@Table(name = "medicine")
public class Medicine {
	private long id;
	private String medicineName;
	// Công ty sản xuất
	private String medicineCompany;
	
	private String category;
	// Xuất xứ
	private String origin;
	// Ngày sản xuất
	private Date manufactureDate;
	private Date expireDate;
	private long amount;
	private String unit;
	private String status;
	private Date importDate;

	public Medicine() {
		
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
	@Column(name = "origin", nullable = false)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "manufacture_date", nullable = false)
	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@Column(name = "expire_date", nullable = false)
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "amount", nullable = false)
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Column(name = "unit", nullable = false)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	@Column(name = "category", nullable = false)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "import_date", nullable = false)
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", medicineName=" + medicineName + ", medicineCompany=" + medicineCompany
				+ ", origin=" + origin + ", manufactureDate=" + manufactureDate + ", expireDate=" + expireDate
				+ ", amount=" + amount + ", unit=" + unit + ", status=" + status + "]";
	}

	
	
}
