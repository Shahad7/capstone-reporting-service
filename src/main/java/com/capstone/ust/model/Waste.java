package com.capstone.ust.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Waste extends Category{

	private double recyclable_waste;
	private double non_recyclable_waste;
	private double emission;
}
