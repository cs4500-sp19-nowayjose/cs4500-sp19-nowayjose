package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.ServiceQuestionAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceQuestionAnswerRepository extends CrudRepository<ServiceQuestionAnswer, Integer> {

    @Query(value="SELECT serviceQuestionAnswer FROM ServiceQuestionAnswer serviceQuestionAnswer")
    public List<ServiceQuestionAnswer> findAllServiceQuestionAnswers();

    @Query(value="SELECT serviceQuestionAnswer FROM ServiceQuestionAnswer serviceQuestionAnswer WHERE id=:id")
    public ServiceQuestionAnswer findServiceQuestionAnswerById(@Param("id") Integer id);

    @Query(value="SELECT serviceQuestionAnswer FROM ServiceQuestionAnswer serviceQuestionAnswer WHERE provider.id=:provider_id AND serviceQuestion.id=:service_question_id")
    public ServiceQuestionAnswer findProvidersAnswerToServiceQuestion(
        @Param("service_question_id") Integer serviceQuestionId,
        @Param("provider_id") Integer userId);

}

