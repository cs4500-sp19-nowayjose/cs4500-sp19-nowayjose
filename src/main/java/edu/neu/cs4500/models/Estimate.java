package edu.neu.cs4500.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="estimate")
public class Estimate {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private float estimate; 
    private float basePrice;
    private Frequency baseFrequency; 
    private boolean subscription; 
    private Frequency subscriptionFrequency; 
    private Frequency deliveryFrequency; 
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getEstimate() {
		return estimate;
	}

	public void setEstimate(float estimate) {
		this.estimate = estimate;
	}

	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public Frequency getBaseFrequency() {
		return baseFrequency;
	}

	public void setBaseFrequency(Frequency baseFrequency) {
		this.baseFrequency = baseFrequency;
	}

	public boolean isSubscription() {
		return subscription;
	}

	public void setSubscription(boolean subscription) {
		this.subscription = subscription;
	}

	public Frequency getSubscriptionFrequency() {
		return subscriptionFrequency;
	}

	public void setSubscriptionFrequency(Frequency subscriptionFrequency) {
		this.subscriptionFrequency = subscriptionFrequency;
	}

	public Frequency getDeliveryFrequency() {
		return deliveryFrequency;
	}

	public void setDeliveryFrequency(Frequency deliveryFrequency) {
		this.deliveryFrequency = deliveryFrequency;
	}

    

}
