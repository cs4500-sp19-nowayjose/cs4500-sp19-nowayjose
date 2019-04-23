package edu.neu.cs4500.services;

import edu.neu.cs4500.models.ServiceProvider;
import edu.neu.cs4500.models.ServiceQuestion;
import edu.neu.cs4500.models.ServiceQuestionAnswer;
import edu.neu.cs4500.repositories.ServiceProviderRepository;
import edu.neu.cs4500.repositories.ServiceQuestionAnswerRepository;
import edu.neu.cs4500.repositories.ServiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@CrossOrigin(origins="*")
public class ServiceQuestionAnswerService {

	public final static String ROUTE = "/api/service_question_answers";

	@Autowired
	ServiceQuestionAnswerRepository answersRepository;

	@Autowired
	ServiceProviderRepository providerRepository;
	@Autowired
	ServiceQuestionRepository serviceQuestionRepository;

	@GetMapping(ROUTE)
	public List<ServiceQuestionAnswer> findAllServiceQuestionAnswers() {
	    List<ServiceQuestionAnswer> sq = answersRepository.findAllServiceQuestionAnswers();
	    return sq;
	}

	@GetMapping(ROUTE + "/{serviceQuestionAnswerId}")
	public ServiceQuestionAnswer findServiceQuestionAnswerById(
			@PathVariable("serviceQuestionAnswerId") Integer id) {
		return answersRepository.findServiceQuestionAnswerById(id);
	}

	@PostMapping(ROUTE)
	public ServiceQuestionAnswer createServiceQuestionAnswer(@RequestBody ServiceQuestionAnswer serviceQuestionAnswer) {
        ServiceProvider provider = providerRepository.findServiceProviderById(serviceQuestionAnswer.getProvider().getId());
        serviceQuestionAnswer.setProvider(provider);

		ServiceQuestion serviceQuestion = serviceQuestionRepository.findServiceQuestionById(serviceQuestionAnswer.getServiceQuestion().getId());
		serviceQuestionAnswer.setServiceQuestion(serviceQuestion);

		Date now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
		serviceQuestionAnswer.setCreatedAt(now);
		serviceQuestionAnswer.setUpdatedAt(now);

		return answersRepository.save(serviceQuestionAnswer);
	}

	@PutMapping(ROUTE + "/{serviceQuestionAnswerId}")
	public ServiceQuestionAnswer updateServiceQuestionAnswer(
			@PathVariable("serviceQuestionAnswerId") Integer id,
			@RequestBody ServiceQuestionAnswer sqaUpdates) {
		ServiceQuestionAnswer sqa = answersRepository.findServiceQuestionAnswerById(id);

		sqa.setUpdatedAt(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
		sqa.setTrueFalseAnswer(sqaUpdates.getTrueFalseAnswer());
		sqa.setMinRangeAnswer(sqaUpdates.getMinRangeAnswer());
		sqa.setMaxRangeAnswer(sqaUpdates.getMaxRangeAnswer());
		sqa.setChoiceAnswer(sqaUpdates.getChoiceAnswer());

		return answersRepository.save(sqa);
	}

	@DeleteMapping(ROUTE + "/{serviceQuestionAnswerId}")
	public String deleteServiceQuestionAnswer(
			@PathVariable("serviceQuestionAnswerId") Integer id) {
		answersRepository.deleteById(id);
		return "Deleted answer " + id;
	}

}
