package edu.neu.cs4500.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*")
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ServiceProviderRepository serviceProviderRepository;

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
  public Optional<User> profile(HttpSession session) {
     User currentUser = (User) session.getAttribute("user");
     return userRepository.findById(currentUser.getId());
  }


	@GetMapping("/api/user/is-service-provider")
	public Boolean isUserServiceProvider(User user) {
		List<User> userFound = (List<User>) userRepository.findByCredentials(user.getUsername(), user.getPassword());
		if (userFound.size() != 1) {
			return false;
		}
		if (userFound.get(0).getProviderDetail() == null) {
			return false;
		}
		return true;
	}

	@PostMapping("/api/user/service-provider/detail")
	public ServiceProvider getServiceProviderDetail(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		if (!isUserServiceProvider(user)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		return user.getProviderDetail();
	}

	@PutMapping("/api/user/service-provider/detail")
	public void updateServiceProviderDetail(@RequestBody ServiceProvider update, @RequestBody User user, HttpSession session, HttpServletResponse response) {
		if (!isUserServiceProvider(user)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else if (update.getPaymentMethod() == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			User updated = updateBusinessInfo(user.getId(), update);
			session.setAttribute("user", updated);
			response.setStatus(200);
		}
	}

	private User updateBusinessInfo(Integer id, ServiceProvider updateInfo) {
		User user = userRepository.findUserById(id);
		ServiceProvider detail = user.getProviderDetail();
		detail.setTitle(updateInfo.getTitle());
		detail.setYearsInBusiness(updateInfo.getYearsInBusiness());
		detail.setEmployees(updateInfo.getEmployees());
		detail.setEmail(updateInfo.getEmail());
		detail.setStreet(updateInfo.getStreet());
		detail.setCity(updateInfo.getCity());
		detail.setState(updateInfo.getState());
		detail.setZipCode(updateInfo.getZipCode());
		detail.setTwitterLink(updateInfo.getTwitterLink());
		detail.setInstagramLink(updateInfo.getInstagramLink());
		detail.setFacebookLink(updateInfo.getFacebookLink());
		detail.setPaymentMethod(updateInfo.getPaymentMethod());
		serviceProviderRepository.save(detail);
		System.out.println(detail.toString());
		return user;
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
		user.setDOBMonth(userUpdates.getDOBMonth());
		user.setDOBDay(userUpdates.getDOBDay());
		user.setDOBYear(userUpdates.getDOBYear());
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