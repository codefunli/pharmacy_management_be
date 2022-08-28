package com.nineplus.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nineplus.pharmacy.entity.MedicineExportEntity;
import com.nineplus.pharmacy.model.ExportData;

@Repository
public interface MedicineExportRepository extends JpaRepository<MedicineExportEntity, Long>{
	
	@Query(name = "getExportData", nativeQuery = true)
	public List<ExportData> getExportData(@Param("month") int month);
	
	@Query(value = "select m from MedicineExportEntity m where m.medicineCode= :medicineCode")
	public List<MedicineExportEntity> getByMedicinCodeLst(String medicineCode);
}
