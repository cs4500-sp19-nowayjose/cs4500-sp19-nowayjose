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
    private static List<ServiceProvider> providers = new ArrayList<>();
    private static ServiceProvider travis;
    private static ServiceProvider lil;
    private static ServiceProvider kanye;
    private static ServiceProvider anotherLil;

    @BeforeAll
    public static void setUpEachTest() {
        setUpUsers();
        setUpServiceQuestions();
        setUpServiceQuestionAnswers();
    }

    private static void setUpUsers() {
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

        anotherLil = new ServiceProvider();
        anotherLil.setId(1200013);
        anotherLil.setTitle("another lil llc");
        anotherLil.setRating(4.1f);
        anotherLil.setYearsInBusiness(3);
        anotherLil.setHires(3);
        anotherLil.setLatestReview("Bad");
        anotherLil.setPrice("$92");
        anotherLil.setIntroduction("Hey I'm lil.");
        anotherLil.setBackgroundChecked(true);
        anotherLil.setEmployees(3);

        providers.add(anotherLil);
        providers.add(travis);
        providers.add(lil);
        providers.add(kanye);
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
        List<SearchCriteria.ScoredProvider> scoredProviders = searchCriteria.calculateProvidersMatchScores(providers);
        assertEquals(scoredProviders.size(), 4);
        assertTrue(scoredProviders.contains(new SearchCriteria.ScoredProvider(travis, 0)));
        assertTrue(scoredProviders.contains(new SearchCriteria.ScoredProvider(lil, 2)));
        assertTrue(scoredProviders.contains(new SearchCriteria.ScoredProvider(anotherLil, 2)));
        assertTrue(scoredProviders.contains(new SearchCriteria.ScoredProvider(kanye, 1)));
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
        providers.add(anotherLil);

        List<SearchCriteria.ScoredProvider> scoredProviders = searchCriteria.calculateProvidersMatchScores(providers);
        searchCriteria.sortProviders(scoredProviders);
        for (int i = 1; i < scoredProviders.size(); i++) {
            SearchCriteria.ScoredProvider p1 = scoredProviders.get(i - 1);
            SearchCriteria.ScoredProvider p2 = scoredProviders.get(i);
            if (p1.score.equals(p2.score)) {
                assertTrue(p2.provider.getTitle().compareTo(p1.provider.getTitle()) >= 0);
            } else {
                assertTrue(p1.score > p2.score);
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
        List<ServiceProvider> searchResults = searchCriteria.orderAndFilterProvidersByScore(providers);
        assertArrayEquals(searchResults.toArray(), new ServiceProvider[]{anotherLil, lil, kanye});
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
        assertEquals(searchCriteria.orderAndFilterProvidersByScore(providers).size(), 0);
    }

    @Test // calvin orderAndFilterProvidersByScore with providers same score
    public void testSortingServiceWithSameScore() {

    }
}