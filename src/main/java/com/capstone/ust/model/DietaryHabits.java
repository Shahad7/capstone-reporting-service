package com.capstone.ust.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaryHabits extends Category{
	private double meat_consumption;
	private double dairy_consumption;
	private double other_consumption;
	private double emission;
}
