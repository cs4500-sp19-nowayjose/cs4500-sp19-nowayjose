package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.models.ServiceQuestion;
import edu.neu.cs4500.models.ServiceQuestionType;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import edu.neu.cs4500.repositories.ServiceQuestionAnswerRepository;
import edu.neu.cs4500.repositories.ServiceQuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceProviderService.class)
public class ServiceProviderSearchTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceQuestionRepository sqr;
    @MockBean
    private ServiceQuestionAnswerRepository sqar;
    @MockBean
    private ServiceProviderRepository spr;


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

    public void setUp() {
        ServiceProvider sp = new ServiceProvider();
        sp.setTitle("bob");
        when(spr.findAllServiceProviders()).thenReturn(Arrays.asList(sp));
    }

    @Test
    public void testFindAllService() throws Exception {
        setUp();
        String jsonString = "{\"zip\": null, \"title\": null, \"filters\": {}}";
        this.mockMvc
                .perform(
                    put("/api/service-provider/filter")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("bob")));
    }

}

