package edu.neu.cs4500.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.User;
import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.repositories.UserRepository;
import edu.neu.cs4500.repositories.FrequentlyAskedAnswerRepository;

@RestController
public class FrequentlyAskedAnswerService {
	@Autowired
	FrequentlyAskedAnswerRepository answerRepository;
	@Autowired
	UserRepository userRepository;

	@PostMapping("/api/faq-answers")
    public FrequentlyAskedAnswer createAnswer(@RequestBody FrequentlyAskedAnswer answer) {
        return answerRepository.save(answer);
    }
	@DeleteMapping("/api/faq-answers/{id}")
	public void deleteFAQAnswer(
			@PathVariable("id") Integer id) {
		answerRepository.deleteById(id);
	}
	@PutMapping("/api/faq-answers/{id}")
	public FrequentlyAskedAnswer updateFAQAnswer(
			@PathVariable("id") Integer id,
			@RequestBody FrequentlyAskedAnswer newAnswer) {
		FrequentlyAskedAnswer answer = answerRepository.findById(id).orElse(null);
		if (answer == null) {
			return null;
		}
		answer.setAnswer(newAnswer.getAnswer());
		return answerRepository.save(answer);
	}
	@PostMapping("/api/users/{userId}/faq-answers")
	public FrequentlyAskedAnswer addFAQAnswerToUser(
			@PathVariable("userId") Integer id,
			@RequestBody FrequentlyAskedAnswer answer) {
		User user = userRepository.findUserById(id);
		answer.setUser(user);
		return answerRepository.save(answer);
	}
	@GetMapping("/api/users/{userId}/faq-answers")
	public List<FrequentlyAskedAnswer> findFAQAnswersForUser(
			@PathVariable("userId") Integer userId) {
		List<FrequentlyAskedAnswer> allAnswers = (List<FrequentlyAskedAnswer>) answerRepository.findAll();
		List<FrequentlyAskedAnswer> result = new ArrayList<>();
		for (FrequentlyAskedAnswer answer: allAnswers) {
			if (answer.getUser().getId() == userId) {
                result.add(answer);
            }
        }
		return result;
	}
	@GetMapping("/api/faq-answers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswers() {
		return (List<FrequentlyAskedAnswer>) answerRepository.findAll();
	}
	@GetMapping("/api/faq-answers/{id}")
	public FrequentlyAskedAnswer findFAQAnswersById(
			@PathVariable("id") Integer id) {
		return answerRepository.findById(id).orElse(null);
	}
}