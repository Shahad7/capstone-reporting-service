package com.capstone.ust.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.capstone.ust.model.DietaryHabits;
import com.capstone.ust.model.Electricity;
import com.capstone.ust.model.FuelSources;
import com.capstone.ust.model.PrivateTransport;
import com.capstone.ust.model.PublicTransport;
import com.capstone.ust.model.Waste;
import com.capstone.ust.model.Water;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document
@AllArgsConstructor
@Data
public class CarbonEmission {
	
	@Id
	private String id;
	@NotBlank(message="userID cant' be null")
	@Size(min=1)
	private String userID;
	@NotBlank(message="date can't be null")
	@Size(min=7)
	private String date;
	private PublicTransport public_transport;
	private PrivateTransport private_transport;
	private Water water;
	private DietaryHabits dietary_habits;
	private Waste waste;
	private FuelSources fuel_sources;
	private Electricity electricity;

	public CarbonEmission() {
        super();
		this.public_transport = new PublicTransport();
		this.private_transport = new PrivateTransport();
		this.water = new Water();
		this.dietary_habits = new DietaryHabits();
		this.waste = new Waste();
		this.fuel_sources = new FuelSources();
		this.electricity = new Electricity();
    }
	
}
