package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity 
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String role;

	// Need an explicit default now
	public User() {
		super();
	}

	// Allows mapping a JSON request with `{"id": id}` as the user field to a User with
	// that id.
	@JsonCreator
	public User(@JsonProperty("id") int id) {
		super();
		this.id = id;
	}

	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<ServiceQuestionAnswer> serviceQuestionAnswers;

	public List<ServiceQuestionAnswer> getServiceQuestionAnswers() {
		return serviceQuestionAnswers;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
