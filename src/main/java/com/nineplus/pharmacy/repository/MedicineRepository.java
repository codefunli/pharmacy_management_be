package com.nineplus.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nineplus.pharmacy.model.Medicine;
import com.nineplus.pharmacy.model.ReportInput;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>{
	
	@Query(name = "getReportData", nativeQuery = true)
	public List<ReportInput> getReportData(@Param("month") int month);
}
