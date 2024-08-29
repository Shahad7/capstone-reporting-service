package com.capstone.ust.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicTransport extends  Category{
	
	private double flight_km;
	private double bus_km;
	private double train_km;
	private double emission;
}
