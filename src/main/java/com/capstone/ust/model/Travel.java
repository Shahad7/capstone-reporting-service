package com.capstone.ust.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Travel {
	
	private double public_transport;
	private double private_transport;
	private double emission;
}
