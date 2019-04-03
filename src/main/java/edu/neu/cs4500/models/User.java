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
}
