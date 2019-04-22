package edu.neu.cs4500.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@PutMapping("/api/users/login")
	public User login(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		List<User> userFound = (List<User>) userRepository.findByCredentials(user.getUsername(), user.getPassword());
		if (userFound.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		else {
			session.setAttribute("user", userFound.get(0));
			return userFound.get(0);
		}
	}
  
  @GetMapping("/api/users/profile")
  public User profile(HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    return currentUser;
  }


	@GetMapping("/api/user/is-service-provider")
	public Boolean isUserServiceProvider(HttpSession session) {
		if (session == null) {
			return false;
		}
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return false;
		}
		if (user.getProviderDetail() == null) {
			return false;
		}
		return true;
	}

	@PutMapping("/api/users/{userId}")
	public User updateUser(
			@PathVariable("userId") Integer id,
			@RequestBody User userUpdates) {
		User user = userRepository.findUserById(id);
		user.setRole(userUpdates.getRole());
		user.setUsername(userUpdates.getUsername());
		user.setFirstName(userUpdates.getFirstName());
		user.setLastName(userUpdates.getLastName());
		user.setDob(userUpdates.getDob());
		user.setAddStreet(userUpdates.getAddStreet());
		user.setAddCity(userUpdates.getAddCity());
		user.setAddState(userUpdates.getAddState());
		user.setAddZip(userUpdates.getAddZip());
		return userRepository.save(user);
	}
	@DeleteMapping("/api/users/{userId}")
	public void deleteUser(
			@PathVariable("userId") Integer id) {
		userRepository.deleteById(id);
	}
}