package com.nineplus.pharmacy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineplus.pharmacy.entity.MedicineEntity;
import com.nineplus.pharmacy.model.ReportInput;
import com.nineplus.pharmacy.repository.MedicineRepository;

@Service
public class MedicineService {
	
	@Autowired
	MedicineRepository medicineRepository;
	
	public List<ReportInput> getReportData(int month){
		List<ReportInput> data = medicineRepository.getReportData(month);
		return data;	
	}
	
	public List<MedicineEntity> getImportData(int month){
		List<MedicineEntity> data = medicineRepository.getImportData(month);
		return data;	
	}
}
