package com.capstone.ust.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashMap;
import java.util.Map;

import com.capstone.ust.exception.CurrentMonthRecordAlreadyExists;
import com.capstone.ust.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.capstone.ust.entity.CarbonEmission;
import com.capstone.ust.exception.NoRecordsFoundForUserException;
import com.capstone.ust.exception.RecordNotFoundException;
import com.capstone.ust.repository.CarbonEmissionRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class CarbonEmissionService {

	@Autowired
	private CarbonEmissionRepository carbonEmissionRepository;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	RestTemplate restTemplate;
	
	public CarbonEmission save(CarbonEmission carbonEmission) throws CurrentMonthRecordAlreadyExists {
		if(mongoTemplate.find(query(where("userID").is(carbonEmission.getUserID()).and("date").is(carbonEmission.getDate())), CarbonEmission.class).size()>=1)
			throw new CurrentMonthRecordAlreadyExists("Current month record already exists for the user");
		return carbonEmissionRepository.save(carbonEmission);
	}
	
	
	public Iterable<CarbonEmission> getRecords(){
		return carbonEmissionRepository.findAll();
	}
	
	
	public Iterable<CarbonEmission> getUserRecords(String userID) throws NoRecordsFoundForUserException{
		Long count = mongoTemplate.query(CarbonEmission.class)
					.matching(query(where("userID").is(userID))).count();
		if(count>=1)
			return carbonEmissionRepository.findByUserID(userID);
		else
			throw new NoRecordsFoundForUserException("No emission records found for the requested user");
	}

	public CarbonEmission getUserRecordByDate(String userId, String date) {
		return carbonEmissionRepository.findByUserIDAndDate(userId, date);
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
	

	
	public CarbonEmission updateRecord(String emission_id, Map<String,Object> map) throws RecordNotFoundException{
		if(carbonEmissionRepository.existsById(emission_id)) {
			Update update = new Update();
			map.forEach((key, value) -> {

                try {

					Class<?> categoryClass = CarbonEmission.class.getDeclaredField(key).getType();

                    if(categoryClass.getSuperclass().getSimpleName().equals("Category")) {
						String url = "http://localhost:5002/api/v1/calculate/"+key;
						HashMap<String,Object> category = restTemplate.postForEntity(url,value,HashMap.class).getBody();
						update.set(key,category);

					}
					else
						update.set(key, value);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            });

			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
	        return mongoTemplate.findById(emission_id, CarbonEmission.class);
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
			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
			return mongoTemplate.findById(emission_id, CarbonEmission.class);
		}
		else
			throw new RecordNotFoundException("No such emission record found");
    }
	

	public CarbonEmission cumulativeUpdateSpecificCategory(String emission_id, String category, Map<String,Object> map) throws RecordNotFoundException {
		if(carbonEmissionRepository.existsById(emission_id)) {
			Update update = new Update();
			@SuppressWarnings("unchecked")
			Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
			innerMap.forEach((key,value)->{
				update.inc(category.concat(".").concat(key),(Number) value);
			});
			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
			return mongoTemplate.findById(emission_id, CarbonEmission.class);
		}
		else
			throw new RecordNotFoundException("No such emission record found");
    }



}
