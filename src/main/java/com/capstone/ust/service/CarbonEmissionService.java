package com.capstone.ust.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.capstone.ust.entity.CarbonEmission;
import com.capstone.ust.exception.NoRecordsFoundForUserException;
import com.capstone.ust.exception.RecordNotFoundException;
import com.capstone.ust.model.Category;
import com.capstone.ust.repository.CarbonEmissionRepository;

@Service
public class CarbonEmissionService {

	@Autowired
	private CarbonEmissionRepository carbonEmissionRepository;
	@Autowired
	MongoTemplate template;
	
	public CarbonEmission save(CarbonEmission carbonEmission) {
		return carbonEmissionRepository.save(carbonEmission);
	}
	
	
	public Iterable<CarbonEmission> getRecords(){
		return carbonEmissionRepository.findAll();
	}
	
	
	public Iterable<CarbonEmission> getUserRecords(String userID) throws NoRecordsFoundForUserException{
		Long count = template.query(CarbonEmission.class)
					.matching(query(where("userID").is(userID))).count();
		if(count>=1)
			return carbonEmissionRepository.findByUserID(userID);
		else
			throw new NoRecordsFoundForUserException("No emission records found for the requested user");
	}
	
	
	public String delete(String id) throws RecordNotFoundException {
		
		if(carbonEmissionRepository.existsById(id)) {
			carbonEmissionRepository.deleteById(id);
			return "Successfully deleted";
		}
		else
			throw new RecordNotFoundException("No such emission record found");
		
	}
	
	
	public CarbonEmission findById(String id) throws RecordNotFoundException {
		if(carbonEmissionRepository.existsById(id)) 
			return carbonEmissionRepository.findById(id).get();
		else		
			throw new RecordNotFoundException("No such emission record found");
	}
	

	
	public CarbonEmission updateRecord(String emission_id, Map<String,Object> map) throws RecordNotFoundException {
		if(carbonEmissionRepository.existsById(emission_id)) {
			Update update = new Update();
			map.forEach((key, value) -> {
				update.set(key, value);
			});
			template.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
	        return template.findById(emission_id, CarbonEmission.class);
		}
		else
			throw new RecordNotFoundException("No such emission record found");
    }



	public CarbonEmission updateSpecificCategory(String emission_id, String category, Map<String,Object> map) throws RecordNotFoundException {
		if(carbonEmissionRepository.existsById(emission_id)) {
			Update update = new Update();
			@SuppressWarnings("unchecked")
			Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
			innerMap.forEach((key,value)->{
				update.set(category.concat(".").concat(key),value);
			});
			template.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
			return template.findById(emission_id, CarbonEmission.class);
		}
		else
			throw new RecordNotFoundException("No such emission record found");
    }
	
	//to modify!
	public CarbonEmission cumulativeUpdateSpecificCategory(String emission_id, String category, Map<String,Object> map) throws RecordNotFoundException {
		if(carbonEmissionRepository.existsById(emission_id)) {
			Update update = new Update();
			@SuppressWarnings("unchecked")
			Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
			innerMap.forEach((key,value)->{
				update.set(category.concat(".").concat(key),value);
			});
			template.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
			return template.findById(emission_id, CarbonEmission.class);
		}
		else
			throw new RecordNotFoundException("No such emission record found");
    }
	
	/*****************CALCULATION ENDPOINTS********************************************************************/
	
	public CarbonEmission calculateAll(String emission_id) {
		
		
		return null;
	}
	
	public Category calculateForSpecificCategory(String emission_id, String category) {
		
		return null;
	}
	
}
