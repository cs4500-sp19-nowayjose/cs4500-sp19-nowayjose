package edu.neu.cs4500.repositories;


import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceCategoryRepository extends CrudRepository<ServiceCategory, Integer> {
  @Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory")
  public List<ServiceCategory> findAllServiceCategories();
  @Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory WHERE serviceCategory.id=:id")
  public ServiceCategory findServiceCategoryById(@Param("id") Integer id);
  @Query(value="SELECT serviceCategory.services FROM ServiceCategory serviceCategory WHERE serviceCategory.id=:id")
  public List<Service> findAllServicesForCategory(@Param("id") Integer id);
}
