package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.ServiceQuestionAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceQuestionAnswerRepository extends CrudRepository<ServiceQuestionAnswer, Integer> {

    @Query(value="SELECT serviceQuestionAnswer FROM ServiceQuestionAnswers serviceQuestionAnswer")
    public List<ServiceQuestionAnswer> findAllServiceQuestionAnswers();

    @Query(value="SELECT serviceQuestionAnswer FROM ServiceQuestionAnswers serviceQuestionAnswer WHERE serviceQuestionAnswer.id=:id")
    public ServiceQuestionAnswer findServiceQuestionAnswerById(@Param("id") Integer id);

    @Query(value="SELECT serviceQuestionAnswer FROM ServiceQuestionAnswers serviceQuestionAnswer WHERE updatedBy.id=:user_id AND serviceQuestion.id=:service_question_id")
    public ServiceQuestionAnswer findUsersAnswerToServiceQuestion(
        @Param("service_question_id") Integer serviceQuestionId,
        @Param("user_id") Integer userId);

}

