package edu.neu.cs4500.services;

import edu.neu.cs4500.models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// To do: connect jupiter with Spring
public class SearchServiceTest {
    private static ServiceQuestionAnswerService sqas = new ServiceQuestionAnswerService();
    private static UserService userService = new UserService();
    private static SearchService searchService = new SearchService();

    private static User travis = new User();
    private static User lil = new User();
    private static User kanye = new User();

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
        travis = new User()
                .setId(1200010)
                .setFirstName("Travis")
                .setLastName("Scott")
                .setRole("Provider")
                .setUsername("SickoMode");
        lil = new User()
                .setId(1200011)
                .setFirstName("Lil")
                .setLastName("Pump")
                .setRole("Provider")
                .setUsername("GucciGang");
        kanye = new User()
                .setId(1200012)
                .setFirstName("Kanye")
                .setLastName("West")
                .setRole("Admin")
                .setUsername("SlowJamz");
        userService.createUser(travis);
        userService.createUser(lil);
        userService.createUser(kanye);
    }

    private static void insertServiceQuestionAnswersForUsers() {
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200010)
                .setServiceQuestion(houseCleaningServiceQuestions.get(0))
                .setMinRangeAnswer(2)
                .setMaxRangeAnswer(3)
                .setUser(travis));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200011)
                .setServiceQuestion(houseCleaningServiceQuestions.get(0))
                .setMinRangeAnswer(3)
                .setMaxRangeAnswer(4)
                .setUser(lil));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200012)
                .setServiceQuestion(houseCleaningServiceQuestions.get(0))
                .setMinRangeAnswer(4)
                .setMaxRangeAnswer(5)
                .setUser(kanye));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200013)
                .setServiceQuestion(houseCleaningServiceQuestions.get(1))
                .setTrueFalseAnswer(false)
                .setUser(travis));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200014)
                .setServiceQuestion(houseCleaningServiceQuestions.get(1))
                .setTrueFalseAnswer(true)
                .setUser(lil));
        sqas.createServiceQuestionAnswer(new ServiceQuestionAnswer()
                .setId(1200015)
                .setServiceQuestion(houseCleaningServiceQuestions.get(1))
                .setTrueFalseAnswer(true)
                .setUser(kanye));
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
                new User[]{travis, kanye});
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
        userService.deleteUser(1200010);
        userService.deleteUser(1200011);
        userService.deleteUser(1200012);
    }

    private static void deleteQuestionAnswers() {
        sqas.deleteServiceQuestionAnswer(1200010);
        sqas.deleteServiceQuestionAnswer(1200011);
        sqas.deleteServiceQuestionAnswer(1200012);
    }
}

