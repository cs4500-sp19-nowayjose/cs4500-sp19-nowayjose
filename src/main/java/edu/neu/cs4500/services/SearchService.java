package edu.neu.cs4500.services;

import edu.neu.cs4500.models.SearchCriteria;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchService {

    @Autowired
    private UserRepository userRepository;

    public List<User> searchForProviders(Service service, SearchCriteria criteria) {
        return criteria.orderAndFilterUsersByScore(userRepository.findAllUsers());
    }

}
