package com.capstone.ust.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Document
@NoArgsConstructor
@AllArgsConstructor
public class CarbonEmission {
	
	@Id
	private String id;
	private double public_transport;
	private double private_transport;
	private double litres_used;
	private double meat_consumption;
	private double dairy_cosumption;
	
	
	
	
}
