package edu.neu.cs4500.repositories;

import java.util.List;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;
import edu.neu.cs4500.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.FrequentlyAskedAnswer;

public interface FrequentlyAskedAnswerRepository  extends CrudRepository<FrequentlyAskedAnswer, Integer>{
    @Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer")
    public List<FrequentlyAskedAnswer> findAllFAQAnswers();

    @Query(value="SELECT frequentlyAskedAnswer FROM FrequentlyAskedAnswer frequentlyAskedAnswer WHERE frequentlyAskedAnswer.id=:id")
    public FrequentlyAskedAnswer findFAQAnswerById(@Param("id") Integer id);

    @Query(value="DELETE FROM FrequentlyAskedAnswer frequentlyAskedAnswer WHERE frequentlyAskedAnswer.user=:user")
    public void deleteFrequentlyAskedAnswerByUser(@Param("uid") User user);

    @Query(value="DELETE FROM FrequentlyAskedAnswer frequentlyAskedAnswer WHERE frequentlyAskedAnswer.frequentlyAskedQuestion=:question")
    public void deleteFrequentlyAskedAnswerByQuestion(@Param("uid") FrequentlyAskedQuestion question);
}