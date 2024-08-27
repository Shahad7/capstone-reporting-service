package com.capstone.ust.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.ust.model.CarbonEmission;
import com.capstone.ust.service.CarbonEmissionService;

@RestController
@RequestMapping("/api/v1")
public class CarbonEmissionController {
	
	@Autowired
	private CarbonEmissionService carbonEmissionService;

	@GetMapping("/ce")
	public Iterable<CarbonEmission> getAll(){
		return carbonEmissionService.getRecords();
	}
	
	@PostMapping("/ce")
	public CarbonEmission createRecord(@RequestBody CarbonEmission carbonEmission) {
		return carbonEmissionService.save(carbonEmission);
		
	}
	
//	@PostMapping("/ce/{ce_id}/{category}")
//	public CarbonEmission putIfAbsentOrAlter(@PathVariable String ce_id,@PathVariable Category) {
//		
//	}
	
	@DeleteMapping("/ce/{ce_id}")
	public String delete(@PathVariable String id) {
		if(carbonEmissionService.delete(id))
			return "successfully deleted";
		else
			return "no such record";
		
	}
} 
