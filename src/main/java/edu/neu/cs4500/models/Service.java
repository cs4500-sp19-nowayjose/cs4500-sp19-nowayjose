package edu.neu.cs4500.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name="services")
public class Service {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String serviceName;
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ServiceQuestion> serviceQuestions;

	@ManyToMany(mappedBy="services")
	private List<ServiceCategory> serviceCategories;
	
	@ManyToMany
    @JsonIgnore
    @JoinTable(
            name="PROVIDERS_SERVICES",
            joinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="USER_ID", referencedColumnName="id"))
    private List<User> providers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<ServiceQuestion> getServiceQuestions() {
		return serviceQuestions;
	}

	public void setServiceQuestions(List<ServiceQuestion> serviceQuestions) {
		this.serviceQuestions = serviceQuestions;
	}

	public List<ServiceCategory> getServiceCategories() {
		return serviceCategories;
	}

	public void setServiceCategories(List<ServiceCategory> serviceCategories) {
		this.serviceCategories = serviceCategories;
	}
}