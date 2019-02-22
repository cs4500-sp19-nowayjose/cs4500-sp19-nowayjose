package edu.neu.cs4500.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="fee")
public class Fee {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private float fee; 
	private Frequency frequency; 
	private Integer additionalMiles; 
	private boolean flat; 
	@ManyToOne
	@JsonIgnore
	private Estimate estimate;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public float getFee() {
		return fee;
	}
	
	public void setFee(float fee) {
		this.fee = fee;
	}
	
	public Frequency getFrequency() {
		return frequency;
	}
	
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	
	public Integer getAdditionalMiles() {
		return additionalMiles;
	}
	
	public void setAdditionalMiles(Integer additionalMiles) {
		this.additionalMiles = additionalMiles;
	}
	
	public boolean isFlat() {
		return flat;
	}
	
	public void setFlat(boolean flat) {
		this.flat = flat;
	}
	
	public Estimate getEstimate() {
		return estimate;
	}
	
	public void setEstimate(Estimate estimate) {
		this.estimate = estimate;
	} 
}
