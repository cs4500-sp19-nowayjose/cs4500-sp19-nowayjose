package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.*;
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
import java.util.*;

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
    public void testGetAllServiceProviders() throws Exception {
        setUp();
        this.mockMvc.perform(get("/api/service-provider"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testAddServiceProvider() throws Exception {
        setUp();
        ServiceProvider newUser = new ServiceProvider();
        newUser.setTitle("yo");
        newUser.setId(1231);

        this.mockMvc
                .perform(
                        post("/api/service-provider")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(asJsonString(newUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("yo")));
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

    @Test
    public void testTitleSearch() throws Exception {
        ServiceProvider sp1 = new ServiceProvider();
        sp1.setTitle("some title");
        ServiceProvider sp2 = new ServiceProvider();
        sp2.setTitle("some title is included");

        String jsonString = "{\"zip\": null, \"title\": \"some title\", \"filters\": {}}";
        when(spr.searchServiceProvidersByTitle("some title")).thenReturn(Arrays.asList(sp1, sp2));
        this.mockMvc
                .perform(
                        put("/api/service-provider/filter")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void testTitleAndZipSearch() throws Exception {
        ServiceProvider sp1 = new ServiceProvider();
        sp1.setTitle("some title");
        sp1.setZipCode("02115");
        String jsonString = "{\"zip\": \"02115\", \"title\": \"some title\", \"filters\": {}}";
        when(spr.searchServiceProvidersByTitleAndZip("02115","some title")).thenReturn(Arrays.asList(sp1));
        this.mockMvc
                .perform(
                        put("/api/service-provider/filter")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].zipCode", is("02115")));
    }

    @Test
    public void testZipOnly() throws Exception {
        ServiceProvider sp1 = new ServiceProvider();
        sp1.setTitle("some title");
        sp1.setZipCode("02115");
        String jsonString = "{\"zip\": \"02115\", \"title\": null, \"filters\": {}}";
        when(spr.searchServiceProvidersByZip("02115")).thenReturn(Arrays.asList(sp1));
        this.mockMvc
                .perform(
                        put("/api/service-provider/filter")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].zipCode", is("02115")));
    }

    @Test
    public void testFilters() throws Exception {
        ServiceProvider sp1 = new ServiceProvider();
        sp1.setId(1);
        sp1.setTitle("some title");
        sp1.setZipCode("02115");
        ServiceQuestion sq = new ServiceQuestion()
            .setId(1)
            .setServiceQuestionType(ServiceQuestionType.YESORNO);
        when(spr.findServiceProviderById(1)).thenReturn(sp1);
        when(sqr.findServiceQuestionById(1)).thenReturn(sq);
        ServiceQuestionAnswer answer = new ServiceQuestionAnswer()
            .setId(1)
            .setProvider(sp1)
            .setServiceQuestion(sq)
            .setTrueFalseAnswer(true);
        LinkedList<ServiceQuestionAnswer> answers = new LinkedList<>();
        answers.add(answer);
        sp1.setServiceQuestionAnswers(answers);
        String jsonString = "{\"zip\": \"02115\", \"title\": null, \"filters\": {\"1\": true}}";
        when(spr.searchServiceProvidersByZip("02115")).thenReturn(Arrays.asList(sp1));
        this.mockMvc
            .perform(
                put("/api/service-provider/filter")
                    .contentType(APPLICATION_JSON_UTF8)
                    .content(jsonString)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].title", is("some title")));
    }


}

