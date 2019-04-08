package edu.neu.cs4500.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*")
public class UserService {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/users")
	public List<User> findAllUser() {
		return userRepository.findAllUsers();
	}
	@GetMapping("/api/users/{userId}")
	public User findUserById(
			@PathVariable("userId") Integer id) {
		return userRepository.findUserById(id);
	}
	@PostMapping("/api/users")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PostMapping("/api/users/register")
	public User register(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		List<User> userFound = (List<User>) userRepository.findByUsername(user.getUsername());
		if (userFound.isEmpty()) {
			session.setAttribute("user", user);
			return userRepository.save(user);
		}
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

	@PutMapping("/api/users/{userId}")
	public User updateUser(
			@PathVariable("userId") Integer id,
			@RequestBody User userUpdates) {
		User user = userRepository.findUserById(id);
		user.setRole(userUpdates.getRole());
		user.setUsername(user.getUsername());
		user.setFirstName(userUpdates.getFirstName());
		user.setLastName(userUpdates.getLastName());
		return userRepository.save(user);
	}
	@DeleteMapping("/api/users/{userId}")
	public void deleteUser(
			@PathVariable("userId") Integer id) {
		userRepository.deleteById(id);
	}
}