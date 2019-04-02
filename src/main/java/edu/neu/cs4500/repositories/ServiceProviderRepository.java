package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceProviderRepository {
    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider")
    public List<Service> findAllServiceProviders();

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider WHERE serviceProvider.id = :id")
    public Service findServiceProviderById(@Param("id") Integer id);

    @Query(value="SELECT serviceProvider FROM ServiceProvider serviceProvider " +
            "WHERE serviceProvider.zipCode = :zip AND serviceProvider.title LIKE CONCAT('%', :title, '%') ")
    public List<ServiceCategory> searchServiceProviders(@Param("id") String zip, @Param("title") String title);
}
