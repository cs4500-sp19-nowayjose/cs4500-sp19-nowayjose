package edu.neu.cs4500.models;

import javax.persistence.*;
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

	public List<ServiceQuestion> getServiceQuestions() {
		return serviceQuestions;
	}

	public void setServiceQuestions(List<ServiceQuestion> serviceQuestions) {
		this.serviceQuestions = serviceQuestions;
	}

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
}