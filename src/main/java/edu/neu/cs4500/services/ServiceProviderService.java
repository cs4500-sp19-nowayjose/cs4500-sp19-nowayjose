package edu.neu.cs4500.services;

import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*")
public class ServiceProviderService {

    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    @RequestMapping(value="/api/service-provider/filter", method = RequestMethod.GET)
    public List<ServiceProvider> filterByTitleAndZip(@RequestParam("title") Optional<String> title,
                                                     @RequestParam("zip") Optional<String> zip) {
        if (title.isPresent() && zip.isPresent()) {
            return serviceProviderRepository.searchServiceProviders(zip.get(), title.get());
        }
        if (title.isPresent() && !zip.isPresent()) {
            System.out.println(title.get());
            return serviceProviderRepository.searchServiceProviders("02115", title.get());
        }
        if (!title.isPresent() && zip.isPresent()) {
            return serviceProviderRepository.searchServiceProviders(zip.get(), "");
        }
        return new ArrayList<>();
    }

    @PostMapping("/api/service-provider")
    @ResponseBody
    public ServiceProvider addServiceProvider(@RequestBody ServiceProvider serviceProvider) {
        return serviceProviderRepository.save(serviceProvider);
    }

}
