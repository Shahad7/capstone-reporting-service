package com.capstone.ust.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.ust.entity.CarbonEmission;
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
	
	public Iterable<CarbonEmission> getUserRecords(String userID){
		return carbonEmissionRepository.findByUserID(userID);
	}
	
	public boolean delete(String id) {
		carbonEmissionRepository.deleteById(id);
		return !carbonEmissionRepository.existsById(id);
		
	}
	
	public CarbonEmission findById(String id) {
		return carbonEmissionRepository.existsById(id) ? carbonEmissionRepository.findById(id).get() : null;
	}
}
