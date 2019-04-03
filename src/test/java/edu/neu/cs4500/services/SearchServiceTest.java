package edu.neu.cs4500.services;

import edu.neu.cs4500.models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// To do: connect jupiter with Spring
public class SearchServiceTest {
    private static ServiceQuestionAnswerService sqas = new ServiceQuestionAnswerService();
    private static ServiceProviderService providerService = new ServiceProviderService();
    private static SearchService searchService = new SearchService();

    private static ServiceProvider travis;
    private static ServiceProvider lil;
    private static ServiceProvider kanye;

    private static List<ServiceQuestion> houseCleaningServiceQuestions;
    private static Service houseCleaningService;
    private static SearchCriteria searchCriteria;
    private static SearchCriteria.QuestionAnswerCriterion questionAnswerCriterion;

    @BeforeAll
    public static void setUp() {
        setUpService();
        setUpServiceQuestions();
        insertUsersToDB();
        insertServiceQuestionAnswersForUsers();
        setUpQuestionCriteria();
    }

    private static void setUpService() {
        houseCleaningService = new Service();
        houseCleaningService
                .setId(94831)
                .setServiceName("House Cleaning");
    }

    private static void setUpServiceQuestions() {
        houseCleaningServiceQuestions = new ArrayList<>();
        houseCleaningServiceQuestions.add(new ServiceQuestion()
                .setId(99201)
                .setTitle("How many rooms?")
                .setDescription("Select the number of rooms that need to be cleaned")
                .setServiceQuestionType(ServiceQuestionType.MINMAX));
        houseCleaningServiceQuestions.add(new ServiceQuestion()
                .setId(99202)
                .setTitle("Do you have any pet?")
                .setDescription("Choose yes or no.")
                .setServiceQuestionType(ServiceQuestionType.YESORNO));
    }

    private static void insertUsersToDB() {
        travis = new ServiceProvider();
        travis.setId(1200010);
        travis.setTitle("Travis llc");
        travis.setRating(4.3f);
        travis.setYearsInBusiness(1);
        travis.setHires(1);
        travis.setLatestReview("Pretty good");
        travis.setPrice("$3");
        travis.setIntroduction("Hey I'm travis.");
        travis.setBackgroundChecked(true);
        travis.setEmployees(1);

        kanye = new ServiceProvider();
        kanye.setId(1200011);
        kanye.setTitle("Kanye llc");
        kanye.setRating(4.2f);
        kanye.setYearsInBusiness(2);
        kanye.setHires(2);
        kanye.setLatestReview("Ok");
        kanye.setPrice("$4");
        kanye.setIntroduction("Hey I'm kanye.");
        kanye.setBackgroundChecked(false);
        kanye.setEmployees(2);

        lil = new ServiceProvider();
        lil.setId(1200012);
        lil.setTitle("lil llc");
        lil.setRating(4.1f);
        lil.setYearsInBusiness(3);
        lil.setHires(3);
        lil.setLatestReview("Bad");
        lil.setPrice("$92");
        lil.setIntroduction("Hey I'm lil.");
        lil.setBackgroundChecked(true);
        lil.setEmployees(3);

        providerService.addServiceProvider(travis);
        providerService.addServiceProvider(lil);
        providerService.addServiceProvider(kanye);
    }

    private static void insertServiceQuestionAnswersForUsers() {
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200010)
                .setServiceQuestion(houseCleaningServiceQuestions.get(0))
                .setMinRangeAnswer(2)
                .setMaxRangeAnswer(3)
                .setProvider(travis));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200011)
                .setServiceQuestion(houseCleaningServiceQuestions.get(0))
                .setMinRangeAnswer(3)
                .setMaxRangeAnswer(4)
                .setProvider(lil));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200012)
                .setServiceQuestion(houseCleaningServiceQuestions.get(0))
                .setMinRangeAnswer(4)
                .setMaxRangeAnswer(5)
                .setProvider(kanye));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200013)
                .setServiceQuestion(houseCleaningServiceQuestions.get(1))
                .setTrueFalseAnswer(false)
                .setProvider(travis));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200014)
                .setServiceQuestion(houseCleaningServiceQuestions.get(1))
                .setTrueFalseAnswer(true)
                .setProvider(lil));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200015)
                .setServiceQuestion(houseCleaningServiceQuestions.get(1))
                .setTrueFalseAnswer(true)
                .setProvider(kanye));
    }

    private static void setUpQuestionCriteria() {
        Map<ServiceQuestion, SearchCriteria.QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(
                houseCleaningServiceQuestions.get(0),
                new SearchCriteria.QuestionAnswerCriterion(Optional.empty(), Optional.of(3), Optional.empty()));
        criteria.put(
                houseCleaningServiceQuestions.get(1),
                new SearchCriteria.QuestionAnswerCriterion(Optional.of(true), Optional.empty(), Optional.empty()));

        searchCriteria = new SearchCriteria(criteria);
    }


    @Test
    public void testSearchForProviders() {
        assertArrayEquals(searchService.searchForProviders(houseCleaningService, searchCriteria).toArray(),
                new ServiceProvider[]{travis, kanye});
    }

    @Test
    public void testSortingOfProviders() {

    }

    @Test // calvin
    public void testSearchResultNumbersReturning() {

    }

    @AfterAll
    public static void destroy() {
        deleteTestingUsers();
        deleteQuestionAnswers();
    }

    private static void deleteTestingUsers() {
        providerService.deleteServiceProvider(1200010);
        providerService.deleteServiceProvider(1200011);
        providerService.deleteServiceProvider(1200012);
    }

    private static void deleteQuestionAnswers() {
        sqas.deleteServiceQuestionAnswer(1200010);
        sqas.deleteServiceQuestionAnswer(1200011);
        sqas.deleteServiceQuestionAnswer(1200012);
    }
}

