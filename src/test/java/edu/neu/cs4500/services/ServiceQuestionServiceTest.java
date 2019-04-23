package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceQuestion;
import edu.neu.cs4500.models.ServiceQuestionAnswer;
import edu.neu.cs4500.models.ServiceQuestionType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceQuestionService.class)
public class ServiceQuestionServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceQuestionRepository repo;

    private Service testService;
    private ServiceQuestion q1;
    private ServiceQuestion q2;
    private List<ServiceQuestion> questions;

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
        testService = new Service();
        testService.setServiceName("service1").setId(1);
        q1 = new ServiceQuestion();
        q2 = new ServiceQuestion();
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

        questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);

        ServiceQuestionAnswer a1 = new ServiceQuestionAnswer()
            .setId(1)
            .setServiceQuestion(q1)
            .setTrueFalseAnswer(true);
        List<ServiceQuestionAnswer> answers = new LinkedList<>();
        answers.add(a1);
        q1.setServiceQuestionAnswers(answers);

        when(repo.findAllServiceQuestions()).thenReturn(questions);
        when(repo.findServiceQuestionById(1)).thenReturn(q1);
        when(repo.findServiceQuestionById(2)).thenReturn(q2);
    }

    @Test
    public void testFindAllService() throws Exception {
        setUp();
        this.mockMvc
                .perform(get("/api/service_question"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Test1")));
    }

    @Test
    public void testFilterServiceBasedOnTitleDescriptionType() throws Exception {
        setUp();

        ServiceQuestion bodyParam = new ServiceQuestion();
        bodyParam.setTitle("Test1").setDescription("");
        this.mockMvc
                .perform(post("/api/service_question/filter")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(bodyParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", containsString("Test1")));

        bodyParam.setTitle("test2").setDescription("wow");
        this.mockMvc
                .perform(post("/api/service_question/filter")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(bodyParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        bodyParam.setTitle("").setDescription("wow");
        this.mockMvc
                .perform(post("/api/service_question/filter")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(bodyParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", containsString("1")));
    }

    @Test
    public void testFindOneQuestion() throws Exception {
        setUp();
        this.mockMvc
                .perform(get("/api/service_question/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test1")));

        this.mockMvc
                .perform(get("/api/service_question/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test2")));

        this.mockMvc
                .perform(get("/api/service_question/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testFindOneServiceAllQuestions() throws Exception {
        setUp();
        this.mockMvc
                .perform(get("/api/service_question/byService/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        this.mockMvc
                .perform(get("/api/service_question/byService/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testFindAllQuestionsByType() throws Exception {
        setUp();
        this.mockMvc
                .perform(get("/api/service_question/byType/yesorno"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        this.mockMvc
                .perform(get("/api/service_question/byType/somedumb"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createAQuestion() throws Exception {
        setUp();
        ServiceQuestion bodyParam = new ServiceQuestion();
        bodyParam.setTitle("Test3").setDescription("this is a new description").setId(4);

        ServiceQuestion q3 = new ServiceQuestion();
        q3.setId(4).setTitle("test3").setDescription("this is a new data");
        when(repo.save(q3)).thenReturn(q3);

        this.mockMvc
                .perform(post("/api/service_question")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(bodyParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("test3")));
    }

    @Test
    public void updateQuestion() throws Exception {
        setUp();
        ServiceQuestion updateParam = new ServiceQuestion();
        updateParam.setTitle("updated title").setDescription("this is a new description").setId(1);

        this.mockMvc
                .perform(put("/api/service_question/0")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(updateParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        when(repo.save(updateParam)).thenReturn(updateParam);

        this.mockMvc
                .perform(put("/api/service_question/1")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(updateParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("updated title")));

        ServiceQuestion q5 = new ServiceQuestion();
        Service s1 = new Service();
        s1.setId(123).setServiceName("hey");
        q5.setId(2).setTitle("updated").setDescription("huh");
        q5.setService(s1);
        Date now = new Date();
        q5.setCreatedAt(now);
        q5.setUpdatedAt(now);
        when(repo.save(q5)).thenReturn(q5);

        this.mockMvc
                .perform(put("/api/service_question/2")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(q5)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("updated")));
    }

    @Test
    public void updateDescriptionForAQuestion() throws Exception {
        setUp();
        ServiceQuestion updateParam = new ServiceQuestion();
        updateParam.setDescription("this is a new description").setId(1);
        when(repo.save(updateParam)).thenReturn(updateParam);

        this.mockMvc
                .perform(put("/api/service_question/1/updateDescription/this is a new description")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(updateParam)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("this is a new description")));
    }

    @Test
    public void deleteOneAnswer() throws Exception {
        setUp();
        this.mockMvc
            .perform(delete("/api/service_question/1")
                         .contentType(APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("Deleted question 1"));
    }

    @Test
    public void deleteOneAnswerOfAQuestion() throws Exception {
        setUp();
        this.mockMvc
            .perform(delete("/api/service_question/1/service_question_answers/1")
                         .contentType(APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("Deleted answer 1 for question 1"));
    }




}

