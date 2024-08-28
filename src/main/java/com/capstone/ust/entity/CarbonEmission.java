package com.capstone.ust.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.capstone.ust.model.DietaryHabits;
import com.capstone.ust.model.Electricity;
import com.capstone.ust.model.FuelSources;
import com.capstone.ust.model.Travel;
import com.capstone.ust.model.Waste;
import com.capstone.ust.model.Water;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarbonEmission {
	
	@Id
	private String id;
	private String userID;
	private String date;
	private Travel travel;
	private Water water;
	private DietaryHabits dietaryHabits;
	private Waste waste;
	private FuelSources fuelSources;
	private Electricity electricity;
	
}
