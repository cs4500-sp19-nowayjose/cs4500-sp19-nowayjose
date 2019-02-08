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
	private List<Question> questions;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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