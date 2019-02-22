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
    private Integer extraMiles; 
    
    @OneToMany(mappedBy="estimate")
    private List<Fee> chargedFees;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getEstimate() {
		float fees = this.getFees(); 
		float discount = this.getDiscount(); 
		this.setEstimate(this.basePrice - fees + discount);
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

	public List<Fee> getChargedFees() {
		return chargedFees;
	}

	public void setChargedFees(List<Fee> chargedFees) {
		this.chargedFees = chargedFees;
	} 

	public Integer getExtraMiles() {
		return extraMiles;
	}

	public void setExtraMiles(Integer extraMiles) {
		this.extraMiles = extraMiles;
	}

	public float getDiscount() {
		return 0;
	}
	public float getFees() {
		float fees = 0; 
		
		for (Fee fee: this.chargedFees) {
			// calculates delivery fee 
			if (fee.getFrequency().equals(this.deliveryFrequency)) {
				if (fee.isFlat()) {
					fees += fee.getFee(); 
				}
				else {
					fees += fee.getFee() * this.basePrice; 
				}
			}
			
			// calculates progressive fee 
			if (fee.getAdditionalMiles().getKey() >= this.extraMiles && 
				fee.getAdditionalMiles().getValue() <= this.extraMiles) {
				fees += fee.getFee() * this.basePrice; 
			}
		}
		return fees; 
	}
    

}
