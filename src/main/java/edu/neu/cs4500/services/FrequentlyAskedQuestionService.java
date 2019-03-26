package edu.neu.cs4500.services;

import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.repositories.FrequentlyAskedQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*")
public class  FrequentlyAskedQuestionService {
    @Autowired
    FrequentlyAskedQuestionRepository questionRepository;

    @PostMapping("/api/faqs")
    public FrequentlyAskedQuestion createFaq(@RequestBody FrequentlyAskedQuestion faq) {
        return questionRepository.save(faq);
    }

    @GetMapping("/api/faqs")
    public List<FrequentlyAskedQuestion> findAllFaqs() {
        return (List<FrequentlyAskedQuestion>) questionRepository.findAll();
    }

    @GetMapping("/api/faqs/{faqId}")
    public FrequentlyAskedQuestion findFaqById(
            @PathVariable("faqId") Integer id) {
        Optional<FrequentlyAskedQuestion> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.orElse(null);
    }

    @GetMapping("api/faq/{faqId}/answers")
    public List<FrequentlyAskedAnswer> findAllAnswersToFaq(
            @PathVariable("faqId") Integer id) {
        Optional<FrequentlyAskedQuestion> optionalQuestion = questionRepository.findById(id);
        FrequentlyAskedQuestion question = optionalQuestion.orElse(null);
        if (question == null) {
            return null;
        }
        return question.getAnswers();
    }

    @PutMapping ("/api/faq/{faqId}")
    public FrequentlyAskedQuestion updateFaqById(
            @PathVariable("faqId") Integer id,
            @RequestBody FrequentlyAskedQuestion newQuestion) {
        Optional<FrequentlyAskedQuestion> optionalQuestion = questionRepository.findById(id);
        FrequentlyAskedQuestion question;
        if (!optionalQuestion.isPresent()) {
            return null;
        }
        else {
            question = optionalQuestion.get();
        }
        question.setQuestion(newQuestion.getQuestion());
        question.setTitle(newQuestion.getTitle());
        return questionRepository.save(question);
    }

    @DeleteMapping("api/faq/{faqId}")
    public void removeFaqById(@PathVariable("faqId") Integer id) {
        questionRepository.deleteById(id);
    }
}
