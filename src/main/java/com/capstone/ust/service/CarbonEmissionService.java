package com.capstone.ust.service;

import com.capstone.ust.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.capstone.ust.entity.CarbonEmission;
import com.capstone.ust.repository.CarbonEmissionRepository;

import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CarbonEmissionService {

	@Autowired
	private CarbonEmissionRepository carbonEmissionRepository;
	@Autowired
	MongoTemplate template;
	@Autowired
	private Helper helper;
	
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

	public CarbonEmission updateRecord(String emission_id, Map<String,Object> map) {
		Update update = new Update();
		map.forEach((key, value) -> {
			update.set(key, value);
		});
		template.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
        return template.findById(emission_id, CarbonEmission.class);
    }



	public CarbonEmission updateSpecificCategory(String emission_id, String category, Map<String,Object> map) {
		Update update = new Update();
		Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
		innerMap.forEach((key,value)->{
			update.set(category.concat(".").concat(key),value);
		});
		template.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
		return template.findById(emission_id, CarbonEmission.class);
    }
}
