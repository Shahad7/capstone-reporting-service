package com.capstone.ust.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.ust.entity.CarbonEmission;

@Repository
public interface CarbonEmissionRepository extends CrudRepository<CarbonEmission, String>{
	
	Iterable<CarbonEmission> findByUserID(String userID);


    CarbonEmission findByUserIDAndDate(String userId, String date);


	boolean existsByUserIDAndDate(String userId, String date);
}
