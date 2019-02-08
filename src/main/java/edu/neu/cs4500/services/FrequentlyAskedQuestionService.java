package edu.neu.cs4500.services;

import edu.neu.cs4500.repositories.FrequentlyAskedQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrequentlyAskedQuestionService {
    @Autowired
    FrequentlyAskedQuestionRepository questionRepository;

    
}
