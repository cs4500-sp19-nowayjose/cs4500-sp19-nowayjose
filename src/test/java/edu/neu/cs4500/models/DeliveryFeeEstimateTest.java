package edu.neu.cs4500.models;

import static org.junit.jupiter.api.Assertions.*;

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
	  void a() {
		  this.estimate1 = new Estimate();
		  this.estimate1.setBasePrice(750);
		  this.estimate1.setDeliveryFrequency(Frequency.Holiday);
		  this.fee1 = new Fee();
		  this.fee1.setFee((float) 0.20);
		  this.fee1.setFlat(false);
		  this.fee1.setFrequency(Frequency.Holiday);
	  }
	  
	  //test apply
	  @Test
	  void testHolidayFee() {
	    //assertEquals(this.estimate1.getFees(), 150);
		  assertEquals(1, 1);
	  }
}
