package edu.neu.cs4500.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;

public interface FrequentlyAskedQuestionRepository  extends CrudRepository<FrequentlyAskedQuestion, Integer>{
	  @Query(value="SELECT frequentlyAskedQuestion FROM FrequentlyAskedQuestion frequentlyAskedQuestion")
	  public List<FrequentlyAskedQuestion> findAllFrequentlyAskedQuestions();
	  
	  @Query(value="SELECT frequentlyAskedQuestion FROM FrequentlyAskedQuestion frequentlyAskedQuestion WHERE frequentlyAskedQuestion.id=:id")
	  public FrequentlyAskedQuestion findFrequentlyAskedQuestionById(@Param("id") Integer id);
}
