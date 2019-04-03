package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.ServiceProvider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Integer> {
    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider")
    public List<ServiceProvider> findAllServiceProviders();

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider WHERE serviceProvider.id = :id")
    public ServiceProvider findServiceProviderById(@Param("id") Integer id);

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider " +
                 "WHERE serviceProvider.zipCode = :zip")
    public List<ServiceProvider> searchServiceProvidersByZip(@Param("zip") String zip);

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider " +
                 "WHERE serviceProvider.title LIKE CONCAT('%', :title, '%') ")
    public List<ServiceProvider> searchServiceProvidersByTitle(@Param("title") String title);

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider " +
            "WHERE serviceProvider.zipCode = :zip AND serviceProvider.title LIKE CONCAT('%', :title, '%') ")
    public List<ServiceProvider> searchServiceProvidersByTitleAndZip(@Param("zip") String zip, @Param("title") String title);
}
