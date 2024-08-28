package com.capstone.ust.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.ust.entity.CarbonEmission;
import com.capstone.ust.service.CarbonEmissionService;
import com.capstone.ust.utils.Helper;

@RestController
@RequestMapping("/api/v1/emissions")
public class CarbonEmissionController {
	
	@Autowired
	private CarbonEmissionService carbonEmissionService;
	
	@Autowired
	private Helper helper;

//	for testing purpose only
	@GetMapping
	public Iterable<CarbonEmission> getAll(){
		return carbonEmissionService.getRecords();
	}
	
	@GetMapping("/{user_id}")
	public Iterable<CarbonEmission> getUserRecords(@PathVariable String user_id){
		return carbonEmissionService.getUserRecords(user_id);
	}
	
	@PostMapping
	public CarbonEmission createRecord(@RequestBody CarbonEmission carbonEmission) {
		return carbonEmissionService.save(carbonEmission);
		
	}
	
	
	@PutMapping("/{emission_id}")
	public CarbonEmission updateRecord(@PathVariable String emission_id,@RequestBody HashMap<String,Object> map) {
		CarbonEmission record = carbonEmissionService.findById(emission_id);
		for(Map.Entry<String,Object> entry: map.entrySet()) {
			try {
				helper.setProperty(record,entry.getKey(),entry.getValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return carbonEmissionService.save(record);
	}
	
	@PutMapping("/{emission_id}/{category}")
	public CarbonEmission updateCategory(@PathVariable String emission_id,@PathVariable String category,@RequestBody HashMap<String,Object> map) {
		
		CarbonEmission record = carbonEmissionService.findById(emission_id);
		Field categoryField = null;
		try {
			categoryField = record.getClass().getDeclaredField(category);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		categoryField.setAccessible(true);
		
			
		return null;
	}
	
	
	@DeleteMapping("/{emission_id}")
	public String delete(@PathVariable String emission_id) {
		if(carbonEmissionService.delete(emission_id))
			return "successfully deleted";
		else
			return "no such record";
		
	}
} 
