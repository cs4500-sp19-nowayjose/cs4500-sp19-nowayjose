package edu.neu.cs4500.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import edu.neu.cs4500.models.SearchCriteria.QuestionAnswerCriterion;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SearchCriteriaTest {
    private static ServiceQuestion uselessQuestion;
    private static ServiceQuestion usefulQuestion;
    private static SearchCriteria searchCriteria;
    private static List<User> users = new ArrayList<>();
    private static User travis;
    private static User lil;
    private static User kanye;
    private static User anotherLil;

    @BeforeAll
    public static void setUpEachTest() {
        setUpUsers();
        setUpServiceQuestions();
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
        anotherLil = new User()
                .setId(1200015)
                .setFirstName("Lil")
                .setLastName("wayne")
                .setRole("Provider")
                .setUsername("DontCry");
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

    private static void setUpServiceQuestions() {
        uselessQuestion = new ServiceQuestion();
        uselessQuestion
                .setId(1)
                .setTitle("Some useless title")
                .setDescription("Wow testing is exciting!")
                .setServiceQuestionType(ServiceQuestionType.MINMAX);
        usefulQuestion = new ServiceQuestion();
        usefulQuestion
            .setId(2)
            .setTitle("Some useful title")
            .setDescription("Wow testing is boring!")
            .setServiceQuestionType(ServiceQuestionType.YESORNO);
    }

    private static void setUpServiceQuestionAnswers() {
        List<ServiceQuestionAnswer> travisAnswers = new ArrayList<>();
        travisAnswers.add(new ServiceQuestionAnswer()
                .setId(1200010)
                .setServiceQuestion(uselessQuestion)
                .setMinRangeAnswer(2)
                .setMaxRangeAnswer(3));
        travisAnswers.add(new ServiceQuestionAnswer()
                .setId(1200011)
                .setServiceQuestion(usefulQuestion)
                .setMinRangeAnswer(5)
                .setTrueFalseAnswer(false));
        travis.setServiceQuestionAnswers(travisAnswers);

        List<ServiceQuestionAnswer> lilAnswers = new ArrayList<>();
        lilAnswers.add(new ServiceQuestionAnswer()
                .setId(1200012)
                .setServiceQuestion(uselessQuestion)
                .setMinRangeAnswer(1)
                .setMaxRangeAnswer(4));
        lilAnswers.add(new ServiceQuestionAnswer()
                .setId(1200013)
                .setServiceQuestion(usefulQuestion)
                .setTrueFalseAnswer(true));
        lil.setServiceQuestionAnswers(lilAnswers);
        anotherLil.setServiceQuestionAnswers((lilAnswers));

        kanye.setServiceQuestionAnswers(Arrays.asList((new ServiceQuestionAnswer()
                .setId(1200014)
                .setServiceQuestion(uselessQuestion)
                .setMinRangeAnswer(1)
                .setMaxRangeAnswer(2))));
    }

    @Test
    public void testUsersOutputCorrectly() {
        QuestionAnswerCriterion uselessCriterion =
            new QuestionAnswerCriterion(Optional.empty(), Optional.of(1), Optional.empty());
        QuestionAnswerCriterion usefulCriterion =
            new QuestionAnswerCriterion(Optional.of(true), Optional.empty(), Optional.empty());
        Map<ServiceQuestion, QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(uselessQuestion, uselessCriterion);
        criteria.put(usefulQuestion, usefulCriterion);
        searchCriteria = new SearchCriteria(criteria);
        List<SearchCriteria.ScoredUser> scoredUsers = searchCriteria.calculateUsersMatchScores(users);
        assertEquals(scoredUsers.size(), 3);
        assertTrue(scoredUsers.contains(new SearchCriteria.ScoredUser(travis, 0)));
        assertTrue(scoredUsers.contains(new SearchCriteria.ScoredUser(lil, 2)));
        assertTrue(scoredUsers.contains(new SearchCriteria.ScoredUser(kanye, 1)));
    }

    @Test
    public void testUserSortedCorrectly() {
        QuestionAnswerCriterion uselessCriterion =
                new QuestionAnswerCriterion(Optional.empty(), Optional.of(1), Optional.empty());
        QuestionAnswerCriterion usefulCriterion =
                new QuestionAnswerCriterion(Optional.of(true), Optional.empty(), Optional.empty());
        Map<ServiceQuestion, QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(uselessQuestion, uselessCriterion);
        criteria.put(usefulQuestion, usefulCriterion);
        searchCriteria = new SearchCriteria(criteria);
        users.add(anotherLil);

        List<SearchCriteria.ScoredUser> scoredUsers = searchCriteria.calculateUsersMatchScores(users);
        searchCriteria.sortUsers(scoredUsers);
        for (int i = 1; i < scoredUsers.size(); i++) {
            SearchCriteria.ScoredUser user1 = scoredUsers.get(i-1);
            SearchCriteria.ScoredUser user2 = scoredUsers.get(i);
            if (user1.score == user2.score) {
                assertTrue(user2.user.getUsername().compareTo(user1.user.getUsername()) > 0);
            } else {
                assertTrue(user1.score > user2.score);
            }
        }
    }

    @Test
    public void testFilteringUsers() {
        QuestionAnswerCriterion uselessCriterion =
            new QuestionAnswerCriterion(Optional.empty(), Optional.of(1), Optional.empty());
        QuestionAnswerCriterion usefulCriterion =
            new QuestionAnswerCriterion(Optional.of(true), Optional.empty(), Optional.empty());
        Map<ServiceQuestion, QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(uselessQuestion, uselessCriterion);
        criteria.put(usefulQuestion, usefulCriterion);
        searchCriteria = new SearchCriteria(criteria);
        List<User> searchResults = searchCriteria.orderAndFilterUsersByScore(users);
        assertArrayEquals(searchResults.toArray(), new User[]{lil, kanye});
    }

    @Test
    public void testScoresCorrectness() {
        QuestionAnswerCriterion uselessCriterion =
                new QuestionAnswerCriterion(Optional.empty(), Optional.of(1), Optional.empty());
        ServiceQuestionAnswer q = new ServiceQuestionAnswer()
                .setId(1200010)
                .setServiceQuestion(uselessQuestion)
                .setMinRangeAnswer(2)
                .setMaxRangeAnswer(3);
        assertEquals(uselessCriterion.scoreAnswerMatch(q), 0);

        QuestionAnswerCriterion choiceCriterion =
                new QuestionAnswerCriterion(Optional.empty(), Optional.of(2), Optional.empty());
        assertEquals(choiceCriterion.scoreAnswerMatch(q), 1);


        QuestionAnswerCriterion multipleCriterion =
                new QuestionAnswerCriterion(Optional.of(false), Optional.of(2), Optional.empty());
        assertEquals(multipleCriterion.scoreAnswerMatch(q), 1);
    }

    @Test
    public void testEmptySearchCriteria() {
        QuestionAnswerCriterion emptyCriterion =
                new QuestionAnswerCriterion(Optional.empty(), Optional.empty(), Optional.empty());
        Map<ServiceQuestion, QuestionAnswerCriterion> criteria = new HashMap<>();
        criteria.put(uselessQuestion, emptyCriterion);
        searchCriteria = new SearchCriteria(criteria);
        assertEquals(searchCriteria.orderAndFilterUsersByScore(users).size(), 0);
    }

    @Test // calvin orderAndFilterUsersByScore with users same score
    public void testSortingServiceWithSameScore() {

    }
}