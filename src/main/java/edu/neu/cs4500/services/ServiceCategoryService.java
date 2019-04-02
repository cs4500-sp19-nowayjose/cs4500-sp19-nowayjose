package edu.neu.cs4500.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;
import edu.neu.cs4500.repositories.ServiceRepository;

@RestController
@CrossOrigin(origins="*")
public class ServiceCategoryService {
	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@GetMapping("/api/categories")
	public List<ServiceCategory> findAllServiceCategories() {
		return serviceCategoryRepository.findAllServiceCategories();
	}
	@GetMapping("/api/categories/{serviceCategoryId}")
	public ServiceCategory findServiceCategoryById(
			@PathVariable("serviceCategoryId") Integer id) {
		return serviceCategoryRepository.findServiceCategoryById(id);
	}

	@GetMapping("/api/categories/{id}/services")
	public List<Service> findAllServicesByCategory(@PathVariable("id") Integer id) {
		ServiceCategory c = serviceCategoryRepository.findServiceCategoryById(id);
		return c.getServices();
	}

	@PostMapping("/api/categories")
	public ServiceCategory createServiceCategory(@RequestBody ServiceCategory serviceCategory) {
		return serviceCategoryRepository.save(serviceCategory);
	}
	
	@PostMapping("/api/categories/{categoryId}/services/{serviceId}")
	public ServiceCategory addServiceToCategory(
			@PathVariable("categoryId") Integer categoryId,
			@PathVariable("serviceId") Integer serviceId) {
				Optional<ServiceCategory> possibleCategory = serviceCategoryRepository.findById(categoryId);
				Service service = serviceRepository.findServiceById(serviceId);
				ServiceCategory category = null;
				if (possibleCategory.isPresent()) {
					category = possibleCategory.get();
					category.addServiceToCategory(service);
				}
				return serviceCategoryRepository.save(category);
				
	}
	@PutMapping("/api/categories/{serviceCategoryId}")
	public ServiceCategory updateServiceCategory(
			@PathVariable("serviceCategoryId") Integer id,
			@RequestBody ServiceCategory serviceUpdates) {
		ServiceCategory serviceCategory = serviceCategoryRepository.findServiceCategoryById(id);
		serviceCategory.setServiceCategoryName(serviceUpdates.getServiceCategoryName());
		return serviceCategoryRepository.save(serviceCategory);
	}
	@DeleteMapping("/api/categories/{serviceCategoryId}")
	public void deleteServiceCategory(
			@PathVariable("serviceCategoryId") Integer id) {
		serviceCategoryRepository.deleteById(id);
	}
}
