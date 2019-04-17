package edu.neu.cs4500.repositories;

import java.util.List;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.FrequentlyAskedAnswer;
import org.springframework.transaction.annotation.Transactional;

public interface FrequentlyAskedAnswerRepository  extends CrudRepository<FrequentlyAskedAnswer, Integer>{
    @Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer")
    public List<FrequentlyAskedAnswer> findAllFAQAnswers();

    @Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer WHERE frequentlyAskedAnswer.id=:id")
    public FrequentlyAskedAnswer findFAQAnswerById(@Param("id") Integer id);

    @Transactional
    public void deleteFrequentlyAskedAnswerByUser(User user);

    @Transactional
    public void deleteFrequentlyAskedAnswerByFrequentlyAskedQuestion(FrequentlyAskedQuestion question);
}