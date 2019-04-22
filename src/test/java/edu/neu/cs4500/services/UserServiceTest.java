package edu.neu.cs4500.services;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository repo;

	@MockBean
	private ServiceProviderRepository spr;

	User user1;

	private static final MediaType APPLICATION_JSON_UTF8 =
			new MediaType(
					MediaType.APPLICATION_JSON.getType(),
					MediaType.APPLICATION_JSON.getSubtype(),
					Charset.forName("utf8"));

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

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
		.perform(get("/api/users/register"))
		.andDo(print())
		.andExpect(status().isForbidden());
	}

	@Test
	public void testUserServiceProviderInfo() throws Exception {
		User providerUser1 = new User();
		providerUser1.setUsername("jinkim");
		providerUser1.setPassword("pass");
		providerUser1.setId(123);

		ServiceProvider detail = new ServiceProvider();
		detail.setTitle("we have the best cleaners, believe me");
		providerUser1.setProviderDetail(detail);
		detail.setId(123);

		when(repo.findAllUsers()).thenReturn(Arrays.asList(providerUser1));
		when(repo.findByCredentials("jinkim", "pass")).thenReturn(Arrays.asList(providerUser1));
		this.mockMvc
				.perform(post("/api/user/service-provider/detail")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(providerUser1)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("we have the best cleaners, believe me")));
	}

	@Test
	public void testNonServiceProvider() throws Exception {
		User providerUser1 = new User();
		providerUser1.setUsername("jinkim");
		providerUser1.setPassword("pass");
		providerUser1.setId(123);

		User dummy = new User();

		when(repo.findAllUsers()).thenReturn(Arrays.asList(providerUser1));
		when(repo.findByCredentials("jinkim", "pass")).thenReturn(Arrays.asList(providerUser1));
		this.mockMvc
				.perform(post("/api/user/service-provider/detail")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(providerUser1)))
				.andDo(print())
				.andExpect(status().is4xxClientError());

		this.mockMvc
				.perform(post("/api/user/service-provider/detail")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(dummy)))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testUpdateProvider() throws Exception {
		User providerUser1 = new User();
		providerUser1.setUsername("jinkim");
		providerUser1.setPassword("pass");
		providerUser1.setId(123);

		ServiceProvider detail = new ServiceProvider();
		detail.setTitle("we have the best cleaners, believe me");
		detail.setId(123);
		providerUser1.setProviderDetail(detail);

		ServiceProvider update = new ServiceProvider();
		update.setTitle("fake news");
		update.setPaymentMethod("Credit Card");

		when(repo.findAllUsers()).thenReturn(Arrays.asList(providerUser1));
		when(repo.findByUsername("jinkim")).thenReturn(Arrays.asList(providerUser1));
		when(repo.findUserById(123)).thenReturn(providerUser1);
		this.mockMvc
				.perform(put("/api/user/service-provider/detail/jinkim")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(update)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("fake news")));
	}

	@Test
	public void testUpdateNonProvider() throws Exception {
		User nonProvider = new User();
		nonProvider.setUsername("jinkim");
		nonProvider.setPassword("pass");
		nonProvider.setId(123);

		ServiceProvider update = new ServiceProvider();
		update.setTitle("fake news");
		update.setPaymentMethod("Credit Card");

		when(repo.findAllUsers()).thenReturn(Arrays.asList(nonProvider));
		when(repo.findByUsername("jinkim")).thenReturn(Arrays.asList(nonProvider));
		when(repo.findUserById(123)).thenReturn(nonProvider);

		this.mockMvc
				.perform(put("/api/user/service-provider/detail/jinkim")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(update)))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testEmptyPaymentMethod() throws Exception {
		User emptyPayment = new User();
		emptyPayment.setUsername("jinkim");
		emptyPayment.setPassword("pass");
		emptyPayment.setId(123);

		ServiceProvider detail = new ServiceProvider();
		detail.setPaymentMethod(null);
		detail.setId(123);
		emptyPayment.setProviderDetail(detail);


		ServiceProvider update = new ServiceProvider();
		update.setTitle("fake news");

		when(repo.findAllUsers()).thenReturn(Arrays.asList(emptyPayment));
		when(repo.findByUsername("jinkim")).thenReturn(Arrays.asList(emptyPayment));
		when(repo.findUserById(123)).thenReturn(emptyPayment);

		this.mockMvc
				.perform(put("/api/user/service-provider/detail/jinkim")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(update)))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testNonuserFoundForUpdate() throws Exception {
		ServiceProvider update = new ServiceProvider();
		update.setTitle("fake news");

		this.mockMvc
				.perform(put("/api/user/service-provider/detail/sdafkwlajf32342")
						.contentType(APPLICATION_JSON_UTF8)
						.content(asJsonString(update)))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}



}
