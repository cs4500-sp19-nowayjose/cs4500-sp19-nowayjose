package edu.neu.cs4500.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionEstimateDiscountTest {

	private static Estimate e1;
	private static Estimate e2;
	private static Estimate e3;
	private static Discount d1;
	private static Discount d2;
	private static Discount flat;
	
	@BeforeAll
    public static void setUp() {
        e1 = new Estimate();
        e1.setBaseFrequency(Frequency.Daily);
        e1.setBasePrice(1000);
        e1.setSubscription(true);
        e1.setSubscriptionFrequency(Frequency.Biweekly);
        
        e2 = new Estimate();
        e2.setBaseFrequency(Frequency.Monthly);
        e2.setBasePrice(600);
        e2.setSubscription(true);
        e2.setSubscriptionFrequency(Frequency.Monthly);
        
        e3 = new Estimate();
        e3.setBaseFrequency(Frequency.Daily);
        e3.setBasePrice(400);
        e3.setSubscription(true);
        e3.setSubscriptionFrequency(Frequency.Daily);
        
        d1 = new Discount();
        d1.setDiscount(.3f);
        d1.setFrequency(Frequency.Biweekly);
        
        d2 = new Discount();
        d2.setFrequency(Frequency.Monthly);
        d2.setDiscount(.2f);
        
        flat = new Discount();
        flat.setFlat(true);
        flat.setFrequency(Frequency.Onetime);
        flat.setDiscount(20);

        List<Discount> discounts1 = new ArrayList();
        List<Discount> discounts2 = new ArrayList();

        discounts1.add(d1);
        discounts1.add(d2);
        discounts2.add(flat);

        e1.setDiscounts(discounts1);
        e2.setDiscounts(discounts1);
        e3.setDiscounts(discounts2);
    }
	
	@Test
	public void testFlatDiscount() {
		assertEquals(20, e3.getDiscount());
		assertEquals(120, e2.getDiscount());
	}

}
