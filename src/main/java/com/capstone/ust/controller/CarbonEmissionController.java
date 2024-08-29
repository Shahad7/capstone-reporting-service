package com.capstone.ust.controller;

import java.util.HashMap;

import com.capstone.ust.exception.CurrentMonthRecordAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.ust.entity.CarbonEmission;
import com.capstone.ust.exception.NoRecordsFoundForUserException;
import com.capstone.ust.exception.RecordNotFoundException;
import com.capstone.ust.service.CarbonEmissionService;

@RestController
@RequestMapping("/api/v1/emissions")
public class CarbonEmissionController {
	
	@Autowired
	private CarbonEmissionService carbonEmissionService;
	

	/********for testing purpose only******/
	@GetMapping
	public ResponseEntity<Iterable<CarbonEmission>> getAll(){
		return ResponseEntity.ok(carbonEmissionService.getRecords());
	}
	
	@GetMapping("/{user_id}")
	public ResponseEntity<Iterable<CarbonEmission>> getUserRecords(@PathVariable String user_id) throws NoRecordsFoundForUserException{
		return ResponseEntity.ok(carbonEmissionService.getUserRecords(user_id));
	}

	//method to get user's emission record only for a specific month
	@GetMapping("/{user_id}/{month}")
    public ResponseEntity<CarbonEmission> getUserRecordByDate(@PathVariable String user_id,@PathVariable String date) throws RecordNotFoundException{
        return ResponseEntity.ok(carbonEmissionService.getUserRecordByDate(user_id,date));
    }
	@PostMapping
	public  ResponseEntity<CarbonEmission> createRecord(@RequestBody CarbonEmission carbonEmission) throws CurrentMonthRecordAlreadyExists {
		
		return ResponseEntity.status(201).body(carbonEmissionService.save(carbonEmission));
		
	}

	@PutMapping("/{emission_id}")
	public ResponseEntity<CarbonEmission> updateRecord(@PathVariable String emission_id,@RequestBody HashMap<String,Object> map) throws RecordNotFoundException {

		return ResponseEntity.status(200).body(carbonEmissionService.updateRecord(emission_id,map));
	}

//	for normally updating nested category fields
	@PutMapping("/{emission_id}/update/{category}")
	public ResponseEntity<CarbonEmission> updateCategory(@PathVariable String emission_id,@PathVariable String category,@RequestBody HashMap<String,Object> map) throws RecordNotFoundException {

		return ResponseEntity.ok(carbonEmissionService.updateSpecificCategory(emission_id,category,map));
	}
	
	//for when user keeps adding consumption details, fields should be cumulatively updated
	@PutMapping("/{emission_id}/cumulate/{category}")
	public ResponseEntity<CarbonEmission> cumulativeUpdateCategory(@PathVariable String emission_id,@PathVariable String category,@RequestBody HashMap<String,Object> map) throws RecordNotFoundException {

		return ResponseEntity.ok(carbonEmissionService.cumulativeUpdateSpecificCategory(emission_id,category,map));
	}

	
	@DeleteMapping("/{emission_id}")
	public ResponseEntity<String> delete(@PathVariable String emission_id) throws RecordNotFoundException {
			return ResponseEntity.ok(carbonEmissionService.delete(emission_id));
			
	}



	
	
}






