package edu.neu.cs4500.services;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;
import edu.neu.cs4500.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*")
public class ServiceService {
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	
	@GetMapping("/api/services/{id}/categories")
	public List<ServiceCategory> getCategoriesForService(
			@PathVariable("serviceId") Integer id) {
		Service service = serviceRepository.findServiceById(id);
		return service.getServiceCategories();
	}

	@GetMapping("/api/services")
	public List<Service> findAllService() {
		return serviceRepository.findAllServices();
	}
	@GetMapping("/api/services/{serviceId}")
	public Service findServiceById(
			@PathVariable("serviceId") Integer id) {
		return serviceRepository.findServiceById(id);
	}
	@PostMapping("/api/services")
	public Service createService(@RequestBody Service service) {
		return serviceRepository.save(service);
	}

	@PostMapping("/api/services/{serviceId}/categories/{categoryId}")
	public Service addCategoryToService(
			@PathVariable("serviceId") Integer serviceId,
			@PathVariable("categoryId") Integer categoryId) {
				Service service = serviceRepository.findServiceById(serviceId);
				Optional<ServiceCategory> possibleCategory = serviceCategoryRepository.findById(categoryId);
				ServiceCategory category = null;
				if (possibleCategory.isPresent()) {
					category = possibleCategory.get();
					service.addCategoryToService(category);
				}
				return serviceRepository.save(service);

	}


	@PutMapping("/api/services/{serviceId}")
	public Service updateService(
			@PathVariable("serviceId") Integer id,
			@RequestBody Service serviceUpdates) {
		Service service = serviceRepository.findServiceById(id);
		service.setServiceName(serviceUpdates.getServiceName());
		return serviceRepository.save(service);
	}
	@DeleteMapping("/api/services/{serviceId}")
	public void deleteService(
			@PathVariable("serviceId") Integer id) {
		serviceRepository.deleteById(id);
	}

	@DeleteMapping("/api/services/{serviceId}/categories/{categoryId}")
	public void deleteCategoryFromService(
			@PathVariable("serviceId") Integer serviceId,
			@PathVariable("categoryId") Integer categoryId) {

		Service service = serviceRepository.findServiceById(serviceId);
		Optional<ServiceCategory> possibleCategory = serviceCategoryRepository.findById(categoryId);
		ServiceCategory category = null;
		if (possibleCategory.isPresent()) {
			category = possibleCategory.get();
			service.removeCategoryFromService(category);
		}
	}
}