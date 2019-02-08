package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
    @Query(value="SELECT service FROM Service service")
    public List<Service> findAllServices();
    @Query(value="SELECT service FROM Service service WHERE service.id=:id")
    public Service findServiceById(@Param("id") Integer id);
    @Query(value="SELECT service.serviceCategories FROM Service service WHERE service.id=:id")
    public List<ServiceCategory> findAllCategoriesForService(@Param("id") Integer id);
}
