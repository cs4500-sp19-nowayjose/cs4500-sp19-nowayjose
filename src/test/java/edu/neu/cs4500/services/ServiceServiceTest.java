package edu.neu.cs4500.services;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;
import edu.neu.cs4500.repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceService.class)
public class ServiceServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceRepository repo;
    @MockBean
    ServiceCategoryRepository serviceCategoryRepository;

    private Service testService;

    public void setUp() {
        testService = new Service();
        testService.setServiceName("service1").setId(1);
        Service a = new Service();
        a.setServiceName("another").setId(2);
        when(repo.searchServices("serv")).thenReturn(Arrays.asList(testService));
    }

    @Test
    public void testSearch() throws Exception {
        setUp();
        this.mockMvc
                .perform(get("/api/services-search/?q=serv"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].serviceName", is("service1")));
    }

}

