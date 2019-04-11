package edu.neu.cs4500.services;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(User.class)
class UserServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService service;

	@MockBean
	private UserRepository repo;

	User user1;

	@BeforeEach
    void create() {
		this.user1 = new User(); 
		this.user1.setUsername("user1");
		this.user1.setPassword("password");
		this.user1.setId(1);
    }
    
    
	@Test
	public void registerUser()
			throws Exception { 

		HttpServletRequest req = new MockHttpServletRequest();

		List<User> emptyList = new ArrayList<User>(); 
	  	when(repo.findByUsername("user1")).thenReturn(emptyList);
		this.mockMvc
		.perform(get("/api/users/register"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username",
				is("user1")))
		.andExpect(jsonPath("$.id",
				is(1))); 
	}
	
	@Test
	public void alreadyRegisteredUser()
			throws Exception { 

		HttpServletRequest req = new MockHttpServletRequest();

		List<User> nonemptyList = new ArrayList<User>(); 
		nonemptyList.add(this.user1);
	  	when(repo.findByUsername("user1")).thenReturn(nonemptyList);
		this.mockMvc
		.perform(get("/api/users/regsiter"))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
}
