package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.SearchCriteria;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceSearch {

    @Autowired
    private UserRepository userRepository;

    public List<User> searchForProviders(Service service, SearchCriteria criteria) {
        return criteria.orderUsersByScore(userRepository.findAllUsers());
    }

}
