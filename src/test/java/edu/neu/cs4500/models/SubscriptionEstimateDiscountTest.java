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
    private static Estimate e4;
    private static Estimate e5;
    private static Discount d1;
	private static Discount d2;
    private static Discount d3;
    private static Discount d4;
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

        e4 = new Estimate();
        e4.setBaseFrequency(Frequency.Biweekly);
        e4.setBasePrice(100);
        e4.setSubscription(true);
        e4.setSubscriptionFrequency(Frequency.Biweekly);

        e5 = new Estimate();
        e5.setBaseFrequency(Frequency.Hourly);
        e5.setBasePrice(10000);
        e5.setSubscription(true);
        e5.setSubscriptionFrequency(Frequency.Hourly);
        
        d1 = new Discount();
        d1.setDiscount(.3f);
        d1.setFrequency(Frequency.Biweekly);
        
        d2 = new Discount();
        d2.setFrequency(Frequency.Monthly);
        d2.setDiscount(.2f);

        d3 = new Discount();
        d3.setFrequency(Frequency.Daily);
        d3.setDiscount(.1f);

        d4 = new Discount();
        d4.setFrequency(Frequency.Hourly);
        d4.setDiscount(.01f);
        
        flat = new Discount();
        flat.setFlat(true);
        flat.setFrequency(Frequency.Onetime);
        flat.setDiscount(20);

        List<Discount> discounts1 = new ArrayList();
        List<Discount> discounts2 = new ArrayList();
        List<Discount> discounts3 = new ArrayList();

        discounts1.add(d1);
        discounts1.add(d2);
        discounts2.add(flat);
        discounts3.add(d1);
        discounts3.add(d2);
        discounts3.add(d3);
        discounts3.add(d4);

        e1.setDiscounts(discounts1);
        e2.setDiscounts(discounts1);
        e3.setDiscounts(discounts2);
        e4.setDiscounts(discounts1);
        e5.setDiscounts(discounts3);
    }
	
	@Test
	public void testFlatRewards() {
		assertEquals(20, e3.getDiscount());
		assertEquals(120, e2.getDiscount());
	}

	@Test
    public void testPercentageDiscount() {

	    assertEquals(480, e2.getEstimate());
        assertEquals(300, e1.getDiscount());
        assertEquals(700, e1.getEstimate());
        assertEquals(e1.getBasePrice() - e1.getDiscount(), e1.getEstimate());
    }

    @Test
    public void testVariousDiscountFrequency() {
        assertEquals(700, e1.getEstimate());
        assertEquals(480, e2.getEstimate());
        assertEquals(380, e3.getEstimate());
        assertEquals(70, e4.getEstimate());
    }

    @Test
    public void testOngoingDiscounts() {
	    assertEquals(9900, e5.getEstimate());
        assertEquals(700, e1.getEstimate());
        assertEquals(480, e2.getEstimate());
        assertEquals(380, e3.getEstimate());
        assertEquals(70, e4.getEstimate());
    }

    @Test
    public void testSubscriptionDiscounts() {
        assertEquals(700, e1.getEstimate());
        assertEquals(380, e3.getEstimate());
    }

    @Test
    public void testFlatDiscounts() {
        assertEquals(20, e3.getDiscount());
    }

}
