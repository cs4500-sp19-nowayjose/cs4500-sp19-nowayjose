package edu.neu.cs4500.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import edu.neu.cs4500.models.SearchCriteria.QuestionAnswerCriterion;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchCriteriaTest {
    private static ServiceQuestion serviceQuestion;
    private static SearchCriteria searchCriteria;
    private static List<User> users = new ArrayList<>();
    private static User travis;
    private static User lil;
    private static User kanye;

    @BeforeAll
    public static void setUpEachTest() {
        setUpUsers();
        setUpServiceQuestion();
        setUpServiceQuestionAnswers();
    }

    private static void setUpUsers() {
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
        users.add(travis);
        users.add(lil);
        users.add(kanye);
    }

    private static void setUpServiceQuestion() {
        serviceQuestion = new ServiceQuestion();
        serviceQuestion
                .setId(1)
                .setTitle("Some useless title")
                .setDescription("Wow testing is exciting!")
                .setServiceQuestionType(ServiceQuestionType.MINMAX);
    }

    private static void setUpServiceQuestionAnswers() {
        List<ServiceQuestionAnswer> answers = new ArrayList<>();
        answers.add(new ServiceQuestionAnswer()
                .setId(1200010)
                .setServiceQuestion(serviceQuestion)
                .setMinRangeAnswer(2)
                .setMaxRangeAnswer(3));
        answers.add(new ServiceQuestionAnswer()
                .setId(1200011)
                .setServiceQuestion(serviceQuestion)
                .setTrueFalseAnswer(false));
        travis.setServiceQuestionAnswers(answers);
        answers = new ArrayList<>();
        answers.add(new ServiceQuestionAnswer()
                .setId(1200011)
                .setServiceQuestion(serviceQuestion)
                .setMinRangeAnswer(3)
                .setMaxRangeAnswer(4));
        answers.add(new ServiceQuestionAnswer()
                .setId(1200014)
                .setServiceQuestion(serviceQuestion)
                .setTrueFalseAnswer(true));
        lil.setServiceQuestionAnswers(answers);
        kanye.setServiceQuestionAnswers(Arrays.asList((new ServiceQuestionAnswer()
                .setId(1200012)
                .setServiceQuestion(serviceQuestion)
                .setMinRangeAnswer(4)
                .setMaxRangeAnswer(5))));
    }

    @Test // adam sortUsers
    public void testScoresCorrectness() {

    }

    @Test // adam filterUsers
    public void testFilteringUsers() {

    }

    @Test
    public void testEmptySearchCriteria() {
        QuestionAnswerCriterion emptyCriterion =
                new QuestionAnswerCriterion(Optional.empty(), Optional.empty(), Optional.empty());
        Map<ServiceQuestion, QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(serviceQuestion, emptyCriterion);
        searchCriteria = new SearchCriteria(criteria);
        assertEquals(searchCriteria.orderUsersByScore(users).size(), 0);
    }

    @Test // calvin orderUsersByScore with users same score
    public void testSortingServiceWithSameScore() {

    }
}