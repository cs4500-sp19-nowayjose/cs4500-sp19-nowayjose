package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

import static java.lang.System.exit;

@Entity
@Table(name="service_providers")
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Float rating;

    private String zipCode;
    private Integer yearsInBusiness;
    private Integer hires;
    private String latestReview;
    private String price;
    private String email;

    private String street;
    private String state;
    private String city;


    @JsonProperty("paymentMethod")
    private String paymentMethod;


    @JsonProperty("twitterLink")
    private String twitterLink;

    @JsonProperty("instagramLink")
    private String instagramLink;

    @JsonProperty("facebookLink")
    private String facebookLink;

    @Column( length = 1000 )
    private String introduction;
    private Boolean backgroundChecked;
    private Integer employees;

    @OneToMany
    @JsonIgnore
    private List<FrequentlyAskedQuestion> faqs;

    @OneToMany(mappedBy="provider")
    private List<ServiceQuestionAnswer> sqas;

    @OneToOne(mappedBy="providerDetail")
    private User user;

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


    public List<FrequentlyAskedQuestion> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<FrequentlyAskedQuestion> faqs) {
        this.faqs = faqs;
    }

    public List<ServiceQuestionAnswer> getServiceQuestionAnswers() {
        return sqas;
    }

    public void setServiceQuestionAnswers(List<ServiceQuestionAnswer> sqas) {
        this.sqas = sqas;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public static boolean isPaymentMethodCorrect(String pm) {
        String[] paymentMethods = pm.split(",");
        for (String s: paymentMethods) {
            if (!PaymentMethodType.contains(s)) {
                return false;
            }
        }
        return true;
    }

    public void setPaymentMethod(String paymentMethod) {
        if (paymentMethod == null) {
            this.paymentMethod = null;
        } else if (isPaymentMethodCorrect(paymentMethod))
            this.paymentMethod = paymentMethod;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", zipCode='" + zipCode + '\'' +
                ", yearsInBusiness=" + yearsInBusiness +
                ", hires=" + hires +
                ", latestReview='" + latestReview + '\'' +
                ", price='" + price + '\'' +
                ", street='" + street + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", twitterLink='" + twitterLink + '\'' +
                ", instagramLink='" + instagramLink + '\'' +
                ", facebookLink='" + facebookLink + '\'' +
                ", introduction='" + introduction +   '\'' +
                ", backgroundChecked=" + backgroundChecked +
                ", employees=" + employees +
                ", sqas = " + sqas +
                 '}';
    }
}