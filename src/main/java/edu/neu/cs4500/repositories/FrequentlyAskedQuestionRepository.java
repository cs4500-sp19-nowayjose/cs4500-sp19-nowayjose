package edu.neu.cs4500.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.neu.cs4500.models.FrequentlyAskedQuestion;

public interface FrequentlyAskedQuestionRepository  extends CrudRepository<FrequentlyAskedQuestion, Integer>{

}
