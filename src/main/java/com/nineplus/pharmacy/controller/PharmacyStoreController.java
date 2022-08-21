package com.nineplus.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nineplus.pharmacy.model.Medicine;
import com.nineplus.pharmacy.repository.MedicineRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class PharmacyStoreController {
	@Autowired
	private MedicineRepository medicineRepository;
	
	@GetMapping("/pharmacies")
	public List<Medicine> getAllMedicine() {
		return medicineRepository.findAll();
	}
	
}
