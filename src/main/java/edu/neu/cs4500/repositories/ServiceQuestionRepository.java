package edu.neu.cs4500.repositories;

import edu.neu.cs4500.models.ServiceQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceQuestionRepository extends CrudRepository<ServiceQuestion, Integer> {

    @Query(value="SELECT serviceQuestion FROM ServiceQuestion serviceQuestion")
    public List<ServiceQuestion> findAllServiceQuestions();

    @Query(value="SELECT serviceQuestion FROM ServiceQuestion serviceQuestion WHERE serviceQuestion.id=:id")
    public ServiceQuestion findServiceQuestionById(
        @Param("id")
            Integer id
    );

    @Query(value="SELECT serviceQuestion " +
            "FROM ServiceQuestion serviceQuestion " +
            "WHERE serviceQuestion.title LIKE %:title% " +
            "   AND serviceQuestion.description LIKE %:description% ")
    public List<ServiceQuestion> findServiceQuestionByTitleAndDescription(
            @Param("title") String title,
            @Param("description") String description
    );

}

