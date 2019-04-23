package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.transaction.Transactional;

import java.sql.Date;
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
  
	@Temporal(value=TemporalType.DATE)
	private Date dob;
  
	private String addStreet;
	private String addCity;
	private String addState;
	private int addZip;
  
	private String role;

	@JsonProperty("propertyDetailId")
	@OneToOne(cascade = CascadeType.ALL)
	private ServiceProvider providerDetail;

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

	public int getId() {
		return id;
	}
	public User setId(int id) {
		this.id = id;
		return this;
	}
	public String getUsername() {
		return username;
	}
	public User setUsername(String username) {
		this.username = username;
		return this;
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
	public User setRole(String role) {
		this.role = role;
		return this;
	}
	public String getFirstName() {
		return firstName;
	}
	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public String getLastName() {
		return lastName;
	}
	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public ServiceProvider getProviderDetail() {
		return providerDetail;
	}

	public void setProviderDetail(ServiceProvider providerDetail) {
		this.providerDetail = providerDetail;
	}
  
  public Date getDob() {
		return this.dob;
	}
	public User setDob(Date dob) {
		this.dob = dob;
		return this;
	}
  public String getAddStreet() {
		return addStreet;
	}
	public User setAddStreet(String addStreet) {
		this.addStreet = addStreet;
		return this;
	}
  public String getAddCity() {
		return addCity;
	}
	public User setAddCity(String addCity) {
		this.addCity = addCity;
		return this;
	}
  public String getAddState() {
		return addState;
	}
	public User setAddState(String addState) {
		this.addState = addState;
		return this;
	}
  public int getAddZip() {
		return addZip;
	}
	public User setAddZip(int addZip) {
		this.addZip = addZip;
		return this;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", addStreet='" + addStreet + '\'' +
				", addCity='" + addCity + '\'' +
				", addState='" + addState + '\'' +
				", addZip=" + addZip +
				", role='" + role + '\'' +
				", providerDetail=" + providerDetail +
				'}';
	}
}
