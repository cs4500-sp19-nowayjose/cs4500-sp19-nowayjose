package edu.neu.cs4500.services;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.repositories.FrequentlyAskedQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrequentlyAskedQuestionService {
    @Autowired
    FrequentlyAskedQuestionRepository questionRepository;

    @PostMapping("/api/faq")
    public FrequentlyAskedQuestion createFaq(@RequestBody FrequentlyAskedQuestion faq) {
        return questionRepository.save(faq);
    }
}
