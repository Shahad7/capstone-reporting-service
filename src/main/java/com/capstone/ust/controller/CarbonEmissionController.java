package com.capstone.ust.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.ust.entity.CarbonEmission;
import com.capstone.ust.exception.CurrentMonthRecordAlreadyExists;
import com.capstone.ust.exception.NoRecordsFoundForUserException;
import com.capstone.ust.exception.RecordNotFoundException;
import com.capstone.ust.model.DietaryHabits;
import com.capstone.ust.model.Electricity;
import com.capstone.ust.model.FuelSources;
import com.capstone.ust.model.PrivateTransport;
import com.capstone.ust.model.PublicTransport;
import com.capstone.ust.model.Waste;
import com.capstone.ust.model.Water;
import com.capstone.ust.service.CarbonEmissionService;

@RestController
@Validated
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
	public  ResponseEntity<CarbonEmission> createRecord(@Valid @RequestBody CarbonEmission carbonEmission) throws CurrentMonthRecordAlreadyExists {
		
		return ResponseEntity.status(201).body(carbonEmissionService.save(carbonEmission));
		
	}

	@PutMapping("/{emission_id}")
	public ResponseEntity<CarbonEmission> updateRecord(@PathVariable String emission_id,@RequestBody HashMap<String,Object> map) throws RecordNotFoundException {

		return ResponseEntity.status(200).body(carbonEmissionService.updateRecord(emission_id,map));
	}
	

	@DeleteMapping("/{emission_id}")
	public ResponseEntity<String> delete(@PathVariable String emission_id) throws RecordNotFoundException {
			return ResponseEntity.ok(carbonEmissionService.delete(emission_id));
			
	}

//	for normally updating nested category fields
	@PutMapping("/{emission_id}/update/{category}")
	public ResponseEntity<CarbonEmission> updateCategory(@PathVariable String emission_id,@PathVariable String category,@RequestBody HashMap<String,Object> map) throws RecordNotFoundException {

		return ResponseEntity.ok(carbonEmissionService.updateSpecificCategory(emission_id,category,map));
	}
//	
//	//for when user keeps adding consumption details, fields should be cumulatively updated
//	@PutMapping("/{emission_id}/cumulate/{category}")
//	public ResponseEntity<CarbonEmission> cumulativeUpdateCategory(@PathVariable String emission_id,@PathVariable String category,@RequestBody HashMap<String,Object> map) throws RecordNotFoundException {
//
//		return ResponseEntity.ok(carbonEmissionService.cumulativeUpdateSpecificCategory(emission_id,category,map));
//	}
	

	/***************cumulative updates *********************************************/
	@PutMapping("/{emission_id}/cumulate/electricity")
	public ResponseEntity<CarbonEmission> cumulateElectricity(@PathVariable String emission_id,@RequestBody Electricity electricity) throws RecordNotFoundException {

		return ResponseEntity.ok(carbonEmissionService.cumulateElectricity(emission_id,electricity));
	}

	@PutMapping("/{emission_id}/cumulate/water")
	public ResponseEntity<CarbonEmission> cumulateWater(@PathVariable String emission_id, @RequestBody Water water) throws RecordNotFoundException {
	    return ResponseEntity.ok(carbonEmissionService.cumulateWater(emission_id, water));
	}

	@PutMapping("/{emission_id}/cumulate/waste")
	public ResponseEntity<CarbonEmission> cumulateWaste(@PathVariable String emission_id, @RequestBody Waste waste) throws RecordNotFoundException {
	    return ResponseEntity.ok(carbonEmissionService.cumulateWaste(emission_id, waste));
	}
	
	@PutMapping("/{emission_id}/cumulate/dietary_habits")
	public ResponseEntity<CarbonEmission> cumulateDietaryHabits(@PathVariable String emission_id, @RequestBody DietaryHabits dietaryHabits) throws RecordNotFoundException {
	    return ResponseEntity.ok(carbonEmissionService.cumulateDietaryHabits(emission_id, dietaryHabits));
	}

	@PutMapping("/{emission_id}/cumulate/private_transport")
	public ResponseEntity<CarbonEmission> cumulatePrivateTransport(@PathVariable String emission_id, @RequestBody PrivateTransport privateTransport) throws RecordNotFoundException {
	    return ResponseEntity.ok(carbonEmissionService.cumulatePrivateTransport(emission_id, privateTransport));
	}

	@PutMapping("/{emission_id}/cumulate/public_transport")
	public ResponseEntity<CarbonEmission> cumulatePublicTransport(@PathVariable String emission_id, @RequestBody PublicTransport publicTransport) throws RecordNotFoundException {
	    return ResponseEntity.ok(carbonEmissionService.cumulatePublicTransport(emission_id, publicTransport));
	}

	@PutMapping("/{emission_id}/cumulate/fuel_sources")
	public ResponseEntity<CarbonEmission> cumulateFuelSources(@PathVariable String emission_id, @RequestBody FuelSources fuelSources) throws RecordNotFoundException {
	    return ResponseEntity.ok(carbonEmissionService.cumulateFuelSources(emission_id, fuelSources));
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@PutMapping("/{emission_id}/update/electricity")
//	public ResponseEntity<CarbonEmission> updateElectricity(@PathVariable String emission_id,@RequestBody Electricity electricity) throws RecordNotFoundException {
//
//		return ResponseEntity.ok(carbonEmissionService.updateElectricity(emission_id,electricity));
//	}
//	
//	@PutMapping("/{emission_id}/update/water")
//	public ResponseEntity<CarbonEmission> updateWater(@PathVariable String emission_id, @RequestBody Water water) throws RecordNotFoundException {
//	    return ResponseEntity.ok(carbonEmissionService.updateWater(emission_id, water));
//	}
//	
//	@PutMapping("/{emission_id}/update/waste")
//	public ResponseEntity<CarbonEmission> updateWaste(@PathVariable String emission_id, @RequestBody Waste waste) throws RecordNotFoundException {
//	    return ResponseEntity.ok(carbonEmissionService.updateWaste(emission_id, waste));
//	}
//
//	@PutMapping("/{emission_id}/update/dietary_habits")
//	public ResponseEntity<CarbonEmission> updateDietaryHabits(@PathVariable String emission_id, @RequestBody DietaryHabits dietaryHabits) throws RecordNotFoundException {
//	    return ResponseEntity.ok(carbonEmissionService.updateDietaryHabits(emission_id, dietaryHabits));
//	}
//
//	@PutMapping("/{emission_id}/update/private_transport")
//	public ResponseEntity<CarbonEmission> updatePrivateTransport(@PathVariable String emission_id, @RequestBody PrivateTransport privateTransport) throws RecordNotFoundException {
//	    return ResponseEntity.ok(carbonEmissionService.updatePrivateTransport(emission_id, privateTransport));
//	}
//	
//	@PutMapping("/{emission_id}/update/public_transport")
//	public ResponseEntity<CarbonEmission> updatePublicTransport(@PathVariable String emission_id, @RequestBody PublicTransport publicTransport) throws RecordNotFoundException {
//	    return ResponseEntity.ok(carbonEmissionService.updatePublicTransport(emission_id, publicTransport));
//	}
//
//	@PutMapping("/{emission_id}/update/fuel_sources")
//	public ResponseEntity<CarbonEmission> updateFuelSources(@PathVariable String emission_id, @RequestBody FuelSources fuelSources) throws RecordNotFoundException {
//	    return ResponseEntity.ok(carbonEmissionService.updateFuelSources(emission_id, fuelSources));
//	}

	
}






