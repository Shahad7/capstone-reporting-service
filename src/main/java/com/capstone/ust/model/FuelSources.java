package com.capstone.ust.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelSources extends Category {

	private double lpg;
	private double firewood;
	private double emission;
}
