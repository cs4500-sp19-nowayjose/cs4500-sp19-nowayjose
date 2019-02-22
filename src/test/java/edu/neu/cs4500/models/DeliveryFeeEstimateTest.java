package edu.neu.cs4500.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryFeeEstimateTest {
	
	Estimate estimate1;
	Estimate estimate2;
	Estimate estimate3;
	Estimate estimate4;
	
	Fee fee1; 
	Fee fee2; 
	Fee fee3; 
	Fee fee4; 
	
	  @BeforeEach
	  void create() {
		  this.estimate1 = new Estimate();
		  this.estimate1.setBasePrice(750);
		  this.estimate1.setDeliveryFrequency(Frequency.Holiday);
		  
		  this.estimate2 = new Estimate();
		  this.estimate2.setBasePrice(750);
		  this.estimate2.setDeliveryFrequency(Frequency.Weekday);
		  
		  this.estimate3 = new Estimate();
		  this.estimate3.setBasePrice(500);
		  this.estimate3.setDeliveryFrequency(Frequency.Emergency);
		  
		  this.fee1 = new Fee();
		  this.fee1.setFee((float) 0.20);
		  this.fee1.setFlat(false);
		  this.fee1.setFrequency(Frequency.Holiday);
		  
		  this.fee2.setFee(50);
		  this.fee2.setFlat(true);
		  this.fee2.setFrequency(Frequency.Holiday);
		  
		  this.fee3.setFee(0);
		  this.fee3.setFlat(true);
		  this.fee3.setFrequency(Frequency.Weekday);
		  
		  this.fee4.setFee(200);
		  this.fee4.setFlat(true);
		  this.fee4.setFrequency(Frequency.Emergency);
	  }
	  
	  //test holiday fee percentage 
	  @Test
	  void testHolidayPercentFee() {
		  ArrayList<Fee> chargedFees = new ArrayList<>(); 
		  chargedFees.add(this.fee1);
		  chargedFees.add(this.fee4);
		  this.estimate1.setChargedFees(chargedFees);
	      assertEquals(this.estimate1.getEstimate(), 900);
	  }
	  
	  //test emergency fee flat 
	  @Test
	  void testEmergencyPercentFee() {
		  this.estimate1.setDeliveryFrequency(Frequency.Emergency);
		  ArrayList<Fee> chargedFees = new ArrayList<>(); 
		  chargedFees.add(this.fee1);
		  chargedFees.add(this.fee4);
		  this.estimate1.setChargedFees(chargedFees);
	      assertEquals(this.estimate3.getEstimate(), 700);
	  }
	  
	  //test holiday fee flat  
	  @Test
	  void testHolidayFlatFee() {
		  ArrayList<Fee> chargedFees = new ArrayList<>(); 
		  chargedFees.add(this.fee2);
		  chargedFees.add(this.fee4);
		  this.estimate1.setChargedFees(chargedFees);
	      assertEquals(this.estimate1.getEstimate(), 800);	
	   }
	  
	  //test weekday no fee   
	  @Test
	  void testWeekdayNoFee() {
		  ArrayList<Fee> chargedFees = new ArrayList<>(); 
		  chargedFees.add(this.fee3);
		  this.estimate1.setChargedFees(chargedFees);
	      assertEquals(this.estimate1.getEstimate(), 750);	
	   }
	  
	  
	  
	  
}
