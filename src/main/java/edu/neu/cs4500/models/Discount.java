package edu.neu.cs4500.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="discount")
public class Discount {
	
	private float discount;
	private Frequency frequency;
	private boolean flat;
	
	
}
