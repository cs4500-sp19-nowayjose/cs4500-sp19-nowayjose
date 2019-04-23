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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceQuestionAnswerService.class)
public class ServiceQuestionAnswerServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceQuestionRepository sqr;
    @MockBean
    private ServiceQuestionAnswerRepository sqar;
    @MockBean
    private ServiceProviderRepository spr;

    ServiceQuestion q2;
    ServiceProvider provider;
    ServiceQuestionAnswer a1;

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
        Service testService = new Service();
        testService.setServiceName("service1").setId(1);

        ServiceQuestion q1 = new ServiceQuestion();
        ServiceQuestion q2 = new ServiceQuestion();
        q2
                .setId(2)
                .setTitle("Test2")
                .setDescription("Testing service 2 hey")
                .setServiceQuestionType(ServiceQuestionType.YESORNO)
                .setService(testService);
        q1
                .setId(1)
                .setTitle("Test1")
                .setDescription("Testing service 1 wow")
                .setServiceQuestionType(ServiceQuestionType.MINMAX)
                .setService(testService);

        provider = new ServiceProvider();
        provider.setTitle("hi");
        provider.setId(1);
        when(spr.findServiceProviderById(1)).thenReturn(provider);

        List<ServiceQuestion> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        when(sqr.findAllServiceQuestions()).thenReturn(questions);
        when(sqr.findServiceQuestionById(1)).thenReturn(q1);
        when(sqr.findServiceQuestionById(2)).thenReturn(q2);

         a1 = new ServiceQuestionAnswer()
            .setId(1)
            .setServiceQuestion(q1)
            .setTrueFalseAnswer(false);
        ServiceQuestionAnswer a2 = new ServiceQuestionAnswer()
            .setId(2)
            .setServiceQuestion(q2)
            .setChoiceAnswer(2);
        List<ServiceQuestionAnswer> answers = new ArrayList<>();
        answers.add(a1);
        answers.add(a2);
        when(sqar.findAllServiceQuestionAnswers()).thenReturn(answers);
        when(sqar.findServiceQuestionAnswerById(1)).thenReturn(a1);
        when(sqar.findServiceQuestionAnswerById(2)).thenReturn(a2);
    }

    @Test
    public void testFindAllAnswers() throws Exception {
        setUp();
        this.mockMvc
                .perform(get("/api/service_question_answers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].trueFalseAnswer", is(false)))
                .andExpect(jsonPath("$[1].choiceAnswer", is(2)));
    }

    @Test
    public void testFindById() throws Exception {
        setUp();
        this.mockMvc
            .perform(get("/api/service_question_answers/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.choiceAnswer", is(2)));
    }

    @Test
    public void testCreate() throws Exception {
        setUp();
        ServiceQuestionAnswer answer = new ServiceQuestionAnswer()
            .setId(3)
            .setServiceQuestion(q2)
            .setProvider(provider)
            .setTrueFalseAnswer(true);
        String jsonString = "{\"id\":3,\"trueFalseAnswer\":true,\"maxRangeAnswer\":null,\"minRangeAnswer\":null," +
                            "\"choiceAnswer\":null,\"provider\":{\"id\":1},\"serviceQuestion\":{\"id\":2}}";

        doReturn(answer).when(sqar).save(any());
        this.mockMvc
            .perform(post("/api/service_question_answers")
                         .contentType(APPLICATION_JSON_UTF8)
                         .content(jsonString))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.trueFalseAnswer", is(true)));
    }

    @Test
    public void testUpdate() throws Exception {
        setUp();
        String jsonString = "{\"id\":1,\"trueFalseAnswer\":false,\"maxRangeAnswer\":null,\"minRangeAnswer\":null," +
                            "\"choiceAnswer\":3,\"provider\":{\"id\":1},\"serviceQuestion\":{\"id\":2}}";
        doReturn(a1).when(sqar).save(any());
        this.mockMvc
            .perform(put("/api/service_question_answers/1")
                         .contentType(APPLICATION_JSON_UTF8)
                         .content(jsonString))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.choiceAnswer", is(3)));
    }

    @Test
    public void testDelete() throws Exception {
        setUp();
        this.mockMvc
            .perform(delete("/api/service_question_answers/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("Deleted answer 2"));
    }

}

