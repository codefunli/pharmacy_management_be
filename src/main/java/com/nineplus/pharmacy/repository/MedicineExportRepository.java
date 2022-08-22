package com.nineplus.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nineplus.pharmacy.model.MedicineExport;

@Repository
public interface MedicineExportRepository extends JpaRepository<MedicineExport, Long>{

}
