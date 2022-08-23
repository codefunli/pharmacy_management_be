package com.nineplus.pharmacy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nineplus.pharmacy.exception.ResourceNotFoundException;
import com.nineplus.pharmacy.model.Medicine;
import com.nineplus.pharmacy.model.MedicineExport;
import com.nineplus.pharmacy.model.MedicineExportRequest;
import com.nineplus.pharmacy.repository.MedicineExportRepository;
import com.nineplus.pharmacy.repository.MedicineRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class PharmacyStoreController {
	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private MedicineExportRepository medicineExportRepository;

	@GetMapping("/pharmacies")
	public List<Medicine> getAllMedicine() {
		return medicineRepository.findAll();
	}

	@GetMapping("/pharmacies/{id}")
	public ResponseEntity<Medicine> getEmployeeById(@PathVariable(value = "id") Long medicineId)
			throws ResourceNotFoundException {
		Medicine medicine = medicineRepository.findById(medicineId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + medicineId));
		return ResponseEntity.ok().body(medicine);
	}

	@PostMapping("/pharmacies")
	public Medicine createMedicine(@Valid @RequestBody Medicine medicine) {
		return medicineRepository.save(medicine);
	}

	@PutMapping("/pharmacies/{id}")
	public ResponseEntity<Medicine> updateMedicine(@PathVariable(value = "id") Long medicineId,

			@Valid @RequestBody Medicine medicineDetail) throws ResourceNotFoundException {
		Medicine medicine = medicineRepository.findById(medicineId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + medicineId));

		medicine.setMedicineName(medicineDetail.getMedicineName());
		medicine.setMedicineCompany(medicineDetail.getMedicineCompany());
		medicine.setCategory(medicineDetail.getCategory());
		medicine.setManufactureDate(medicineDetail.getManufactureDate());
		medicine.setExpireDate(medicineDetail.getExpireDate());
		medicine.setAmount(medicineDetail.getAmount());
		medicine.setUnit(medicineDetail.getUnit());
		medicine.setStatus(medicineDetail.getStatus());

		final Medicine updatedMedicine = medicineRepository.save(medicine);
		return ResponseEntity.ok(updatedMedicine);
	}

	@DeleteMapping("/pharmacies/{id}")
	public Map<String, Boolean> deleteMedicine(@PathVariable(value = "id") Long medicineId)
			throws ResourceNotFoundException {
		Medicine medicine = medicineRepository.findById(medicineId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + medicineId));

		medicineRepository.delete(medicine);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/pharmacies/export")
	public List<MedicineExport> getAllMedicineExport() {
		return medicineExportRepository.findAll();
	}
	
	@PostMapping("/pharmacies/export")
	public List<MedicineExport> exportMidicineById(@Valid @RequestBody MedicineExportRequest medicineExportRequest) throws ResourceNotFoundException {
		long amount = medicineRepository.getById(medicineExportRequest.getMedicineId()).getAmount();
		long diff = amount - medicineExportRequest.getAmount();
		// Save into Medicine Export DB
		String medicineName = medicineRepository.getById(medicineExportRequest.getMedicineId()).getMedicineName();
		String medicineCompany = medicineRepository.getById(medicineExportRequest.getMedicineId()).getMedicineCompany();
		MedicineExport medicineExport = new MedicineExport();
		medicineExport.setAmount(medicineExportRequest.getAmount());
		medicineExport.setMedicineName(medicineName);
		medicineExport.setMedicineCompany(medicineCompany);
		medicineExport.setExportDate(medicineExportRequest.getExportDate());
		medicineExportRepository.save(medicineExport);
		
		//Update medicine DB
		Medicine medicine = medicineRepository.getById(medicineExportRequest.getMedicineId());
		if(diff>0) {
			medicine.setAmount(diff);
			medicineRepository.save(medicine);
		} else {
			medicineRepository.delete(medicine);
		}
		
		
		return medicineExportRepository.findAll();
	}

}
