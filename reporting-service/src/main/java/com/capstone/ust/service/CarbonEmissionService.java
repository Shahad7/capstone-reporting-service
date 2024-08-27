package com.capstone.ust.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.ust.model.CarbonEmission;
import com.capstone.ust.repository.CarbonEmissionRepository;

@Service
public class CarbonEmissionService {

	@Autowired
	private CarbonEmissionRepository carbonEmissionRepository;
	
	public CarbonEmission save(CarbonEmission carbonEmission) {
		return carbonEmissionRepository.save(carbonEmission);
	}
	
	public Iterable<CarbonEmission> getRecords(){
		return carbonEmissionRepository.findAll();
	}
	
	public boolean delete(String id) {
		carbonEmissionRepository.deleteById(id);
		return carbonEmissionRepository.existsById(id);
		
	}
}
