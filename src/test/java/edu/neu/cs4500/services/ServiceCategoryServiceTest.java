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

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.models.Service;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceCategory.class)
class ServiceCategoryServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ServiceCategoryService service;

	ServiceCategory serviceCategory1;
    ServiceCategory serviceCategory2;

    Service service1;
    Service service2;
    
    @BeforeEach
    void create() {
		Service service1 = new Service();
        this.service1.setId(1);
        this.service1.setServiceName("Appliances");

        this.service2 = new Service();
        this.service2.setId(1);
        this.service2.setServiceName("Gardening");

        this.serviceCategory1 = new ServiceCategory();
        this.serviceCategory1.setId(1);
        this.serviceCategory1.setServiceCategoryName("Home");
        this.serviceCategory1.setServices(new ArrayList<Service>());

        this.serviceCategory2 = new ServiceCategory();
        this.serviceCategory2.setId(2);
        this.serviceCategory2.setServiceCategoryName("Outdoor");
		
    }
    
    
	@Test
	public void getAllServiceCategories()
			throws Exception {
		ArrayList<ServiceCategory> serviceCategories = new ArrayList<>(); 
		serviceCategories.add(this.serviceCategory1);
		serviceCategories.add(this.serviceCategory2);

		when(service.findAllServiceCategories()).thenReturn(serviceCategories);
		this.mockMvc
		.perform(get("/api/categories"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.serviceCategories",
				hasSize(2)))
		.andExpect(jsonPath("$.serviceCategories[*].serviceCategoryName",
				containsInAnyOrder("Home", "Outdoor")));  
	}
	
	@Test
	public void getServiceCategoryById()
			throws Exception {

		when(service.findServiceCategoryById(1)).thenReturn(this.serviceCategory1);
		this.mockMvc
		.perform(get("/api/categories/1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.serviceCategoryName",
				is("Home")))
		.andExpect(jsonPath("$.id",
				is(1)));
	}
	
	@Test
	public void getServicesForCategoryById()
			throws Exception {
        ArrayList<Service> lst = new ArrayList<Service>();
        lst.add(this.service1);
        lst.add(this.service2);
        this.serviceCategory2.setServices(lst);
		
		when(service.findAllServicesByCategory(2)).thenReturn(lst);
		this.mockMvc
		.perform(get("/api/categories/2/services"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.services",
				hasSize(2)))
		.andExpect(jsonPath("$.services[*].serviceName",
				containsInAnyOrder("Appliances", "Gardening")));  
	}
}