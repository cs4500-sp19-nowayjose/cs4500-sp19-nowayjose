package edu.neu.cs4500.services;

import edu.neu.cs4500.models.SearchCriteria;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import edu.neu.cs4500.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchService {

    @Autowired
    private ServiceProviderRepository providerRepository;

    public List<ServiceProvider> searchForProviders(Service service, SearchCriteria criteria) {
        return criteria.orderAndFilterProvidersByScore(providerRepository.findAllServiceProviders());
    }

}
