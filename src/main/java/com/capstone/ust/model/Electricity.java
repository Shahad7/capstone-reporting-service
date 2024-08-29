package com.capstone.ust.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Electricity extends Category {

	private double kwh_used;
	private double emission;
}
