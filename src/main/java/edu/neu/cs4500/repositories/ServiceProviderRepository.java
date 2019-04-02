package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.ServiceProvider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Integer> {
    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider")
    public List<ServiceProvider> findAllServiceProviders();

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider WHERE serviceProvider.id = :id")
    public ServiceProvider findServiceProviderById(@Param("id") Integer id);

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider " +
            "WHERE serviceProvider.zipCode = :zip AND serviceProvider.title LIKE CONCAT('%', :title, '%') ")
    public List<ServiceProvider> searchServiceProviders(@Param("zip") String zip, @Param("title") String title);
}
