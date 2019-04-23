package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.SearchCriteria;
import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.models.ServiceQuestion;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import edu.neu.cs4500.repositories.ServiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins="*")
public class ServiceProviderService {

    @Autowired
    ServiceProviderRepository serviceProviderRepository;
    @Autowired
    ServiceQuestionRepository serviceQuestionRepository;

    /**
     * Request should look like:
     * {
     *     "title": filterForTitle
     *     "zip": filterForZip
     *     "filters": {
     *          questionId: answer (true/false, number, must be appropriate for question type)
     *     }
     * }
     */
    @PutMapping("/api/service-provider/filter")
    public List<ServiceProvider> filterByTitleAndZip(@RequestBody String searchQueryJson) throws IOException {
        System.out.println(searchQueryJson);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(searchQueryJson);

        Optional<String> title = Optional.ofNullable(root.path("title").asText(null));
        Optional<String> zip = Optional.ofNullable(root.path("zip").asText(null));
        List<ServiceProvider> matchingProviders = new ArrayList<>();
        if (title.isPresent() && zip.isPresent()) {
            matchingProviders = serviceProviderRepository.searchServiceProvidersByTitleAndZip(zip.get(), title.get());
        } else if (zip.isPresent()) {
            matchingProviders = serviceProviderRepository.searchServiceProvidersByZip(zip.get());
        } else if (title.isPresent()) {
            matchingProviders = serviceProviderRepository.searchServiceProvidersByTitle(title.get());
        } else {
            matchingProviders = serviceProviderRepository.findAllServiceProviders();
        }

        boolean hasFilter = root
            .get("filters")
            .elements()
            .hasNext();
        if (!hasFilter) {
            return matchingProviders;
        } else {
            SearchCriteria sq = buildSearchCriteria(root.get("filters"));
            return sq.orderAndFilterProvidersByScore(matchingProviders);
        }
    }

    @GetMapping("/api/service-provider")
    @ResponseBody
    public List<ServiceProvider> getAllServiceProviders() {
        return serviceProviderRepository.findAllServiceProviders();
    }

    @PostMapping("api/service-provider")
    public ServiceProvider addServiceProvider(@RequestBody ServiceProvider serviceProvider) {
        serviceProviderRepository.save(serviceProvider);
        return serviceProvider;
    }

    @DeleteMapping("/api/service-provider/{id}")
    public void deleteServiceProvider(@RequestParam("id") Integer id) {
        serviceProviderRepository.deleteById(id);
    }

    private SearchCriteria.QuestionAnswerCriterion buildQuestionAnswerCriterion(ServiceQuestion sq, JsonNode criterionJson) {
        Optional<Boolean> trueFalseAnswer = Optional.empty();
        Optional<Integer> rangeAnswer = Optional.empty();
        Optional<Integer> choiceAnswer = Optional.empty();
        switch (sq.getServiceQuestionType()) {
            case MINMAX:
                rangeAnswer = Optional.of(criterionJson.asInt()); break;
            case MULTIPLECHOICES:
                choiceAnswer = Optional.of(criterionJson.asInt()); break;
            case YESORNO:
                trueFalseAnswer = Optional.of(criterionJson.asBoolean());
                break;
        }
        return new SearchCriteria.QuestionAnswerCriterion(trueFalseAnswer, rangeAnswer, choiceAnswer);
    }

    private SearchCriteria buildSearchCriteria(JsonNode filterQuery) throws IOException {
        HashMap<ServiceQuestion, SearchCriteria.QuestionAnswerCriterion> criteria = new HashMap<>();
        filterQuery.fields().forEachRemaining(filterEntry -> {
            ServiceQuestion sq = serviceQuestionRepository.findServiceQuestionById(Integer.parseInt(filterEntry.getKey()));
            SearchCriteria.QuestionAnswerCriterion criterion = buildQuestionAnswerCriterion(sq, filterEntry.getValue());
            criteria.put(sq, criterion);
        });
        return new SearchCriteria(criteria);
    }

}
