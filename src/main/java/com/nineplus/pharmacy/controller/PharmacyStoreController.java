package com.nineplus.pharmacy.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.nineplus.pharmacy.exception.ResourceNotFoundException;
import com.nineplus.pharmacy.model.Medicine;
import com.nineplus.pharmacy.model.MedicineExport;
import com.nineplus.pharmacy.model.MedicineExportRequest;
import com.nineplus.pharmacy.repository.MedicineExportRepository;
import com.nineplus.pharmacy.repository.MedicineRepository;
import com.nineplus.pharmacy.services.ExportReportService;
import com.nineplus.pharmacy.services.MedicinePDFExporter;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class PharmacyStoreController {
	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private MedicineExportRepository medicineExportRepository;

	@Autowired
	private ExportReportService exportReportService;

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

		medicine.setMedicineCode(medicineDetail.getMedicineCode());
		medicine.setMedicineName(medicineDetail.getMedicineName());
		medicine.setMedicineCompany(medicineDetail.getMedicineCompany());
		medicine.setLotCode(medicineDetail.getLotCode());
		medicine.setRegisterCode(medicineDetail.getRegisterCode());
		medicine.setCriteriaManufacture(medicineDetail.getCriteriaManufacture());
		medicine.setSpecPackage(medicineDetail.getSpecPackage());
		medicine.setCost(medicineDetail.getCost());
		medicine.setConcentration(medicineDetail.getConcentration());
		medicine.setUsageForm(medicineDetail.getUsageForm());		
		medicine.setCost(medicineDetail.getCost());
		medicine.setOrigin(medicineDetail.getOrigin());
		medicine.setManufactureDate(medicineDetail.getManufactureDate());
		medicine.setExpireDate(medicineDetail.getExpireDate());
		medicine.setAmount(medicineDetail.getAmount());
		medicine.setUnit(medicineDetail.getUnit());

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
	public List<MedicineExport> exportMidicineById(@Valid @RequestBody MedicineExportRequest medicineExportRequest)
			throws ResourceNotFoundException {
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

		// Update medicine DB
		Medicine medicine = medicineRepository.getById(medicineExportRequest.getMedicineId());
		if (diff > 0) {
			medicine.setAmount(diff);
			medicineRepository.save(medicine);
		} else {
			medicineRepository.delete(medicine);
		}
		return medicineExportRepository.findAll();
	}

//	@GetMapping("pharmacies/report1")
//	public ResponseEntity<?> export() {
//		try {
//			exportReportService.export();
//			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (ResourceNotFoundException e) {
//			e.getMessage();
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
	
	@GetMapping("pharmacies/report")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Medicine> listMedicine = medicineRepository.findAll();
         
        MedicinePDFExporter exporter = new MedicinePDFExporter(listMedicine);
        exporter.export(response);
         
    }
	
	@GetMapping("/pharmacies/download/{month}")
    public ResponseEntity<Resource> download(@PathVariable(value = "month") String month) throws IOException, ResourceNotFoundException {
    	File file = null;
    	int monthRes = Integer.parseInt(month);
			try {
				file = exportReportService.export(monthRes);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		if (ObjectUtils.isEmpty(file)) {
			throw new ResourceNotFoundException("Data not found");
		}
		String fileName = file.getName();
		String fileNm = fileName.replace(fileName.substring(fileName.lastIndexOf("_"), fileName.length()), "_thang_" + month);
        HttpHeaders header = new HttpHeaders();
        header.add("File-Name", fileNm);
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName );
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Response-Header","content-diposition");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource;
        try {
			 resource = new ByteArrayResource(Files.readAllBytes(path));
		} catch (IOException e) {
			throw new ResourceNotFoundException("Error!");
		}
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                .body(resource);      
    }

}
