package edu.neu.cs4500.services;

import edu.neu.cs4500.models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SearchServiceTest {

    ServiceQuestionAnswerService sqas = new ServiceQuestionAnswerService();
    UserService userService = new UserService();

    User travis = new User();
    User lil = new User();
    User kanye = new User();

    List<ServiceQuestion> houseCleaningServiceQuestions;
    Service houseCleaningService;
    SearchCriteria searchCriteria;
    SearchCriteria.QuestionAnswerCriterion questionAnswerCriterion;

    @BeforeAll
    public void setUp() {
        setUpService();
        setUpServiceQuestions();
        insertUsersToDB();
        insertServiceQuestionAnswersForUsers();
        setUpQuestionCriteria();
    }

    private void setUpService() {
        houseCleaningService = new Service();
        houseCleaningService
                .setId(94831)
                .setServiceName("House Cleaning");
    }

    private void setUpServiceQuestions() {
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

    private void insertUsersToDB() {
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

    private void insertServiceQuestionAnswersForUsers() {
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

    private void setUpQuestionCriteria() {
        Map<ServiceQuestion, SearchCriteria.QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(
                houseCleaningServiceQuestions.get(0),
                new SearchCriteria.QuestionAnswerCriterion(Optional.empty(), Optional.of(3), Optional.empty()));
        criteria.put(
                houseCleaningServiceQuestions.get(1),
                new SearchCriteria.QuestionAnswerCriterion(Optional.of(true), Optional.empty(), Optional.empty()));

        searchCriteria = new SearchCriteria(criteria);
    }


    @Test // jin
    public void testSearchForProviders() {

    }

    @Test // jin
    public void testSortingOfProviders() {

    }

    @Test // calvin
    public void testSearchResultNumbersReturning() {

    }

    @AfterAll
    public void destroy() {
        deleteTestingUsers();
    }

    private void deleteTestingUsers() {
        userService.deleteUser(1200010);
        userService.deleteUser(1200011);
        userService.deleteUser(1200012);
        sqas.deleteServiceQuestionAnswer(1200010);
        sqas.deleteServiceQuestionAnswer(1200011);
        sqas.deleteServiceQuestionAnswer(1200012);

    }




}

