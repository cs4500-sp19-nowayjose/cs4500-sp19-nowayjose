package edu.neu.cs4500.services;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.repositories.FrequentlyAskedQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FrequentlyAskedQuestionService {
    @Autowired
    FrequentlyAskedQuestionRepository questionRepository;

    @PostMapping("/api/faq")
    public FrequentlyAskedQuestion createFaq(@RequestBody FrequentlyAskedQuestion faq) {
        return questionRepository.save(faq);
    }

    @GetMapping
    public List<FrequentlyAskedQuestion> findAllFaqs() {
        return (List<FrequentlyAskedQuestion>) questionRepository.findAll();
    }
}
