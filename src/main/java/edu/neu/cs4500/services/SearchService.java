package edu.neu.cs4500.services;

import edu.neu.cs4500.models.SearchCriteria;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.ServiceQuestionRepository;
import edu.neu.cs4500.repositories.ServiceRepository;
import edu.neu.cs4500.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class SearchService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ServiceQuestionRepository serviceQuestionRepository;

    @GetMapping("/api/provider-search/{serviceId}")
    public List<User> searchProvidersForService(@PathVariable("serviceId") Integer serviceId)
    {
        return userRepository.findAllUsers();
    }

    List<User> searchForProviders(Service service, SearchCriteria criteria) {
        return criteria.orderAndFilterUsersByScore(userRepository.findAllUsers());
    }

}
