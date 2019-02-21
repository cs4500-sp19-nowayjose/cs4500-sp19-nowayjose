package edu.neu.cs4500;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.services.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServiceSearchTest {

    SearchService serviceSearch;
    Service houseCleaningService;

    @BeforeEach
    public void setUp() {
        houseCleaningService = new Service();
        houseCleaningService.setServiceName("House Cleaning");
        serviceSearch = new SearchService();
    }

    @Test
    public void testScoresCorrectness() {

    }

    @Test
    public void testListOfProvidersReturningCorrectly() {

    }

    @Test
    public void testSearchResultSorting() {

    }

    @Test
    public void testSearchResultNumbersReturning() {

    }

    @Test
    public void testEmptySearchCriteria() {

    }

    @Test
    public void testSortingServiceWithSameScore() {

    }

}

