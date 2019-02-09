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
	@PostMapping("/api/answer")
    public FrequentlyAskedAnswer createAnswer(@RequestBody FrequentlyAskedAnswer answer) {
        return answerRepository.save(answer);
    }
	@DeleteMapping("/api/answer/{id}")
	public void deleteFAQAnswer(
			@PathVariable("id") Integer id) {
		answerRepository.deleteById(id);
	}
	@PutMapping("/api/answer/{id}")
	public FrequentlyAskedAnswer updateFAQAnswer(
			@PathVariable("id") Integer id,
			@RequestBody FrequentlyAskedAnswer newAnswer) {
		FrequentlyAskedAnswer answer = answerRepository.findFAQAnswerById(id);
		answer.setAnswer(newAnswer.getAnswer());
		answer.setFrequentlyAskedQuestion(newAnswer.getFrequentlyAskedQuestion());
		answer.setUser(newAnswer.getUser());
		answer.setQuestion(newAnswer.getQuestion());
		return answerRepository.save(answer);
	}
	@PostMapping("/api/users/{userId}/answers")
	public FrequentlyAskedAnswer addFAQAnswerToUser(
			@PathVariable("userId") Integer id,
			@RequestBody FrequentlyAskedAnswer answer) {
		User user = userRepository.findUserById(id);
		answer.setUser(user);
		return answerRepository.save(answer);
	}
	@GetMapping("/api/users/{userId}/answers")
	public List<FrequentlyAskedAnswer> findFAQAnswersForUser(
			@PathVariable("userId") Integer userId) {
		List<FrequentlyAskedAnswer> allAnswers = answerRepository.findAllFAQAnswers();
		List<FrequentlyAskedAnswer> result = new ArrayList<>();
		for (FrequentlyAskedAnswer answer: allAnswers) {
			if (answer.getUser().getId() == userId) {
                result.add(answer);
            }
        }
		return result;
	}
	@GetMapping("/api/answers")
	public List<FrequentlyAskedAnswer> findAllFrequentlyAskedAnswers() {
		return answerRepository.findAllFAQAnswers();
	}
	@GetMapping("/api/answers/{id}")
	public FrequentlyAskedAnswer findFAQAnswersById(
			@PathVariable("id") Integer id) {
		return answerRepository.findFAQAnswerById(id);
	}
}