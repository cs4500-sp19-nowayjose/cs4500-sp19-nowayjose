package edu.neu.cs4500.services;

import edu.neu.cs4500.models.*;
import edu.neu.cs4500.repositories.ServiceQuestionAnswerRepository;
import edu.neu.cs4500.repositories.ServiceQuestionRepository;
import edu.neu.cs4500.repositories.ServiceRepository;
import edu.neu.cs4500.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class ServiceQuestionAnswerService {

	public final static String ROUTE = "/api/service_question_answers";

	@Autowired
	ServiceQuestionAnswerRepository answersRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	ServiceQuestionRepository serviceRepository;

	@GetMapping(ROUTE)
	public List<ServiceQuestionAnswer> findAllServiceQuestionAnswers() {
		return answersRepository.findAllServiceQuestionAnswers();
	}

	@GetMapping(ROUTE + "/{serviceQuestionAnswerId}")
	public ServiceQuestionAnswer findServiceQuestionAnswerById(
			@PathVariable("serviceQuestionAnswerId") Integer id) {
		return answersRepository.findServiceQuestionAnswerById(id);
	}

	@PostMapping(ROUTE)
	public ServiceQuestionAnswer createServiceQuestionAnswer(@RequestBody ServiceQuestionAnswer serviceQuestionAnswer) {
        User answerer = userRepository.findUserById(serviceQuestionAnswer.getUser().getId());
        serviceQuestionAnswer.setUser(answerer);

		ServiceQuestion serviceQuestion = serviceRepository.findServiceQuestionById(serviceQuestionAnswer.getServiceQuestion().getId());
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
	public void deleteServiceQuestionAnswer(
			@PathVariable("serviceQuestionAnswerId") Integer id) {
		answersRepository.deleteById(id);
	}

}
