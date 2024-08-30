package com.capstone.ust.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
import com.capstone.ust.repository.CarbonEmissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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
				if(value!=null) {		
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
				}
            });

			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
	        return mongoTemplate.findById(emission_id, CarbonEmission.class);
		}
		else
			throw new RecordNotFoundException("No such emission record found");
    }
	

	/**************cumulative updates*******************************************/

	public CarbonEmission cumulateElectricity(String emission_id, Electricity electricity) {
		// TODO Auto-generated method stub
		return null;
	}


	public CarbonEmission cumulateWater(String emission_id, Water water) {
		// TODO Auto-generated method stub
		return null;
	}


	public CarbonEmission cumulateWaste(String emission_id, Waste waste) {
		// TODO Auto-generated method stub
		return null;
	}


	public CarbonEmission cumulateDietaryHabits(String emission_id, DietaryHabits dietaryHabits) {
		// TODO Auto-generated method stub
		return null;
	}


	public CarbonEmission cumulatePrivateTransport(String emission_id, PrivateTransport privateTransport) {
		// TODO Auto-generated method stub
		return null;
	}


	public CarbonEmission cumulatePublicTransport(String emission_id, PublicTransport publicTransport) {
		// TODO Auto-generated method stub
		return null;
	}


	public CarbonEmission cumulateFuelSources(String emission_id, FuelSources fuelSources) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	
/************************* follows the @PUT methods for all categories with @PathVariabel category*************/

		//@PUT specific category
		public CarbonEmission updateSpecificCategory(String emission_id, String category, Map<String,Object> map) throws RecordNotFoundException {
			Update update;
			if(carbonEmissionRepository.existsById(emission_id)) {
				CarbonEmission record = carbonEmissionRepository.findById(emission_id).get();
				update = new Update();
				@SuppressWarnings("unchecked")
			
				Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
				if(innerMap!=null) {
					for(Map.Entry<String,Object> entry:innerMap.entrySet()) {
						update.set(category.concat(".").concat(entry.getKey()),entry.getValue());
					}
				}		
				else
					update.set(category,null);
				mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
				
				record = carbonEmissionRepository.findById(emission_id).get();
				update = new Update();
				try {
					
	            	Class<?> categoryClass = CarbonEmission.class.getDeclaredField(category).getType();           	
	            	Field wantedCategory = CarbonEmission.class.getDeclaredField(category); 
	            	wantedCategory.setAccessible(true);
	            	ObjectMapper mapper = new ObjectMapper();
	            	HashMap<String,Object> updatedCategory = mapper.convertValue(wantedCategory.get(record), HashMap.class);
	            	if(innerMap!=null) {
	    				for(Map.Entry<String,Object> entry:innerMap.entrySet()) {
	    					updatedCategory.put(entry.getKey(), entry.getValue());
	    				}
	
	            	}
	            	String url = "http://localhost:5002/api/v1/calculate/"+category;
					HashMap<String,Object> calculated = restTemplate.postForEntity(url,updatedCategory,HashMap.class).getBody();
					
					update.set(category,calculated);
					
	            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
	                throw new RuntimeException(e);
	           }
				mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
				return mongoTemplate.findById(emission_id, CarbonEmission.class);
			}
			else
				throw new RecordNotFoundException("No such emission record found");
	 }

	
	
	
	
	
	
	
	
	
//	public CarbonEmission cumulativeUpdateSpecificCategory(String emission_id, String category, Map<String,Object> map) throws RecordNotFoundException {
//		Update update;
//		if(carbonEmissionRepository.existsById(emission_id)) {
//			CarbonEmission record = carbonEmissionRepository.findById(emission_id).get();
//			update = new Update();
//			@SuppressWarnings("unchecked")
//		
//			Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
//			if(innerMap!=null) {
//				for(Map.Entry<String,Object> entry:innerMap.entrySet()) {
//					update.inc(category.concat(".").concat(entry.getKey()),(Number)entry.getValue());
//				}
//			}		
//			else
//				update.set(category,null);
//			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
//			
//			record = carbonEmissionRepository.findById(emission_id).get();
//			update = new Update();
//			try {
//				
//            	Class<?> categoryClass = CarbonEmission.class.getDeclaredField(category).getType();           	
//            	Field wantedCategory = CarbonEmission.class.getDeclaredField(category); 
//            	wantedCategory.setAccessible(true);
//            	ObjectMapper mapper = new ObjectMapper();
//            	HashMap<String,Object> updatedCategory = mapper.convertValue(wantedCategory.get(record), HashMap.class);
//            	if(innerMap!=null) {
//    				for(Map.Entry<String,Object> entry:innerMap.entrySet()) {
//    					updatedCategory.put(entry.getKey(), entry.getValue());
//    				}
//
//            	}
//            	String url = "http://localhost:5002/api/v1/calculate/"+category;
//				HashMap<String,Object> calculated = restTemplate.postForEntity(url,updatedCategory,HashMap.class).getBody();
//				
//				update.set(category,calculated);
//				
//            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//           }
//			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
//			return mongoTemplate.findById(emission_id, CarbonEmission.class);
//		}
//		else
//			throw new RecordNotFoundException("No such emission record found");
// }

//	public CarbonEmission cumulativeUpdateSpecificCategory(String emission_id, String category, Map<String,Object> map) throws RecordNotFoundException {
//		if(carbonEmissionRepository.existsById(emission_id)) {
//			Update update = new Update();
//			@SuppressWarnings("unchecked")
//			Map<String,Object> innerMap = (Map<String, Object>) map.get(category);
//			innerMap.forEach((key,value)->{
//				update.inc(category.concat(".").concat(key),(Number) value);
//			});
//			mongoTemplate.updateFirst(query(where("_id").is(emission_id)), update, CarbonEmission.class);
//			return mongoTemplate.findById(emission_id, CarbonEmission.class);
//		}
//		else
//			throw new RecordNotFoundException("No such emission record found");
//    }
}
