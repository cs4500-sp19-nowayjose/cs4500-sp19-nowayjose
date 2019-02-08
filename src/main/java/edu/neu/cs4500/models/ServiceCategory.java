package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="service_categories")
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String serviceCategoryName;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name="CATEGORIES_SERVICES",
            joinColumns=@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"))
    private List<Service> services;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}