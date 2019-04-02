package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="service_providers")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Float rating;
    private List<Float> ratingScores;
    private String zipCode;
    private Integer yearsInBusiness;
    private Integer hires;
    private String latestReview;
    private String price;
    private String introduction;
    private Boolean backgroundChecked;
    private Integer employees;
    private List<String> paymentMethods;

    @OneToMany
    private List<FrequentlyAskedQuestion> faqs;
    private List<String> reviews;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<Float> getRatingScores() {
        return ratingScores;
    }

    public void setRatingScores(List<Float> ratingScores) {
        this.ratingScores = ratingScores;
    }

    public Integer getYearsInBusiness() {
        return yearsInBusiness;
    }

    public void setYearsInBusiness(Integer yearsInBusiness) {
        this.yearsInBusiness = yearsInBusiness;
    }

    public Integer getHires() {
        return hires;
    }

    public void setHires(Integer hires) {
        this.hires = hires;
    }

    public String getLatestReview() {
        return latestReview;
    }

    public void setLatestReview(String latestReview) {
        this.latestReview = latestReview;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getBackgroundChecked() {
        return backgroundChecked;
    }

    public void setBackgroundChecked(Boolean backgroundChecked) {
        this.backgroundChecked = backgroundChecked;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<FrequentlyAskedQuestion> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<FrequentlyAskedQuestion> faqs) {
        this.faqs = faqs;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

}