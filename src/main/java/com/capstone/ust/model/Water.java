package com.capstone.ust.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Water extends Category{

	private double litres_used;
	private double emission;
}
