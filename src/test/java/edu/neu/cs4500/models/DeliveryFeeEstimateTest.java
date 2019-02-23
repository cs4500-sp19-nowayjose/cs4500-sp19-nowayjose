package edu.neu.cs4500.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

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
    Fee fee5;
    Fee fee6;
    Fee fee7;

    @BeforeEach
    void create() {
        this.estimate1 = new Estimate();
        this.estimate1.setBasePrice(750);
        this.estimate1.setDeliveryFrequency(Frequency.Holiday);
        this.estimate1.setDiscounts(new ArrayList<Discount>());

        this.estimate2 = new Estimate();
        this.estimate2.setBasePrice(750);
        this.estimate2.setDeliveryFrequency(Frequency.Weekday);
        this.estimate2.setDiscounts(new ArrayList<Discount>());

        this.estimate3 = new Estimate();
        this.estimate3.setBasePrice(500);
        this.estimate3.setDeliveryFrequency(Frequency.Emergency);
        this.estimate3.setDiscounts(new ArrayList<Discount>());

        this.estimate4 = new Estimate();
        this.estimate4.setBasePrice(100);
        this.estimate4.setDiscounts(new ArrayList<>());

        this.fee1 = new Fee();
        this.fee1.setFee((float) 0.20);
        this.fee1.setFlat(false);
        this.fee1.setFrequency(Frequency.Holiday);

        this.fee2 = new Fee();
        this.fee2.setFee(50);
        this.fee2.setFlat(true);
        this.fee2.setFrequency(Frequency.Holiday);

        this.fee3 = new Fee();
        this.fee3.setFee(0);
        this.fee3.setFlat(true);
        this.fee3.setFrequency(Frequency.Weekday);

        this.fee4 = new Fee();
        this.fee4.setFee(200);
        this.fee4.setFlat(true);
        this.fee4.setFrequency(Frequency.Emergency);

        this.fee5 = new Fee();
        this.fee5.setFee((float) 0.1);
        this.fee5.setAdditionalMiles(new ArrayList<>(Arrays.asList(10, 30)));

        this.fee6 = new Fee();
        this.fee6.setFee((float) 0.3);
        this.fee6.setAdditionalMiles(new ArrayList<>(Arrays.asList(31, 70)));

        this.fee7 = new Fee();
        this.fee7.setFee((float) 0.5);
        this.fee7.setAdditionalMiles(new ArrayList<>(Arrays.asList(71, 150)));

    }

    //test holiday fee percentage
    @Test
    void testHolidayPercentFee() {
        ArrayList<Fee> chargedFees = new ArrayList<>();
        chargedFees.add(this.fee1);
        chargedFees.add(this.fee4);
        this.estimate1.setChargedFees(chargedFees);
        assertEquals(900, this.estimate1.getEstimate());
    }

    //test emergency fee flat
    @Test
    void testEmergencyFlatFee() {
        ArrayList<Fee> chargedFees = new ArrayList<>();
        chargedFees.add(this.fee1);
        chargedFees.add(this.fee4);
        this.estimate3.setChargedFees(chargedFees);
        assertEquals(700, this.estimate3.getEstimate());
    }

    //test emergency fee percentage
    @Test
    void testEmergencyPercentFee() {
        ArrayList<Fee> chargedFees = new ArrayList<>();
        this.fee1.setFrequency(Frequency.Emergency);
        chargedFees.add(this.fee1);
        this.estimate3.setChargedFees(chargedFees);
        assertEquals(600, this.estimate3.getEstimate());
    }

    //test emergency percentage no fee
    @Test
    void testEmergencyFlatNoFee() {
        ArrayList<Fee> chargedFees = new ArrayList<>();
        chargedFees.add(this.fee1);
        chargedFees.add(this.fee4);
        this.fee4.setFee(0);
        this.estimate3.setChargedFees(chargedFees);
        assertEquals(500, this.estimate3.getEstimate());
    }

    //test holiday fee flat
    @Test
    void testHolidayFlatFee() {
        ArrayList<Fee> chargedFees = new ArrayList<>();
        chargedFees.add(this.fee2);
        chargedFees.add(this.fee4);
        this.estimate1.setChargedFees(chargedFees);
        assertEquals(800, this.estimate1.getEstimate());
    }

    //test weekday no fee
    @Test
    void testWeekdayNoFee() {
        ArrayList<Fee> chargedFees = new ArrayList<>();
        chargedFees.add(this.fee3);
        this.estimate1.setChargedFees(chargedFees);
        assertEquals(750, this.estimate1.getEstimate());
    }

    //test additional miles where it doesn't apply
    @Test
    void testProgressiveFeeNoFee() {
        ArrayList<Fee>  chargedFees = new ArrayList<>(Arrays.asList(fee5, fee6, fee7));
        this.estimate4.setChargedFees(chargedFees);
        this.estimate4.setExtraMiles(5);
        assertEquals(100, estimate4.getEstimate());
    }

    //test additional miles where it applies at lower bound
    @Test
    void testProgressiveFeeLowerBound() {
        ArrayList<Fee>  chargedFees = new ArrayList<>(Arrays.asList(fee5, fee6, fee7));
        this.estimate4.setChargedFees(chargedFees);
        this.estimate4.setExtraMiles(10);
        assertEquals(110, estimate4.getEstimate());
    }

    //test additional miles where it applies at upper bound
    @Test
    void testProgressiveFeeUpperBound() {
        ArrayList<Fee>  chargedFees = new ArrayList<>(Arrays.asList(fee5, fee6, fee7));
        this.estimate4.setChargedFees(chargedFees);
        this.estimate4.setExtraMiles(70);
        assertEquals(130, estimate4.getEstimate());
    }

    //test additional miles where it applies at middle
    @Test
    void testProgressiveFee() {
        ArrayList<Fee>  chargedFees = new ArrayList<>(Arrays.asList(fee5, fee6, fee7));
        this.estimate4.setChargedFees(chargedFees);
        this.estimate4.setExtraMiles(100);
        assertEquals(150, estimate4.getEstimate());
    }
}
