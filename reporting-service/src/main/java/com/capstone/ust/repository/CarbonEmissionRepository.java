package com.capstone.ust.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.ust.model.CarbonEmission;

@Repository
public interface CarbonEmissionRepository extends CrudRepository<CarbonEmission, String>{
	
}
