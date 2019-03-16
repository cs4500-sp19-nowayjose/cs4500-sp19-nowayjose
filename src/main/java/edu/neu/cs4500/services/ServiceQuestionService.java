package edu.neu.cs4500.services;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import edu.neu.cs4500.models.ServiceQuestion;
import edu.neu.cs4500.repositories.ServiceQuestionRepository;

@RestController
@CrossOrigin(origins="*")
public class ServiceQuestionService {
  @Autowired
  ServiceQuestionRepository serviceQuestionRepository;

  // for Admin to view all service questions
  @GetMapping("api/service_question")
  public List<ServiceQuestion> findAllService() {
    return serviceQuestionRepository.findAllServiceQuestions();
  }

  @PostMapping("api/service_question/filter")
  public List<ServiceQuestion> filterServiceBasedOnTitleDescriptionType(
          @RequestBody ServiceQuestion body
  ) {
    return serviceQuestionRepository
            .findAllServiceQuestions()
            .stream()
            .filter(serviceQuestion ->
              serviceQuestion.getTitle().toLowerCase().contains(body.getTitle().toLowerCase())
                && serviceQuestion.getDescription().toLowerCase().contains(body.getDescription().toLowerCase()))
            .collect(Collectors.toList());
  }

  // for Admin find one question by question id
  @GetMapping("api/service_question/{serviceQuestionId}")
  public ServiceQuestion findOneQuestion(@PathVariable("serviceQuestionId") Integer id) {
    return serviceQuestionRepository.findServiceQuestionById(id);
  }

  // for Admin find all questions for one service
  @GetMapping("api/service_question/byService/{serviceID}")
  public List<ServiceQuestion> findOneServiceAllQuestions(
          @PathVariable("serviceID") Integer id) {
    List<ServiceQuestion> list =
            serviceQuestionRepository.findAllServiceQuestions();
    List<ServiceQuestion> temp = new ArrayList<>();
    for (ServiceQuestion question: list) {
      if (question.getService().getId().equals(id)) {
        temp.add(question);
      }
    }
    return temp;
  }

  // find all questions by question type
  @GetMapping("api/service_question/byType/{type}")
  public List<ServiceQuestion> findAllQuestionsByType(
          @PathVariable("type") String type
  )
  {
    List<ServiceQuestion> list =
            serviceQuestionRepository.findAllServiceQuestions();
    List<ServiceQuestion> temp = new ArrayList<>();
    for (ServiceQuestion question: list) {
      if (question.getServiceQuestionType().equals(type)) {
        temp.add(question);
      }
    }
    return temp;
  }

  // Admin add a question
  @PostMapping("api/service_question")
  public ServiceQuestion createAQuestion(
          @RequestBody ServiceQuestion question) {
    return serviceQuestionRepository.save(question);
  }



  // to update a question
  @PutMapping("api/service_question/{serviceQuestionId}")
  public ServiceQuestion updateQuestion(
          @PathVariable("serviceQuestionId") Integer id,
          @RequestBody ServiceQuestion updatedQuestion) {
    ServiceQuestion target =
            serviceQuestionRepository.findServiceQuestionById(id);
    target.setTitle(updatedQuestion.getTitle());
    target.setServiceQuestionType(updatedQuestion.getServiceQuestionType());

    // optional
    if (updatedQuestion.getCreatedAt() != null){
      target.setCreatedAt(updatedQuestion.getCreatedAt());
    }

    if (updatedQuestion.getDescription() != null) {
      target.setDescription(updatedQuestion.getDescription());
    }

    if(updatedQuestion.getUpdatedAt() != null){
      target.setUpdatedAt(updatedQuestion.getUpdatedAt());
    }

    if (updatedQuestion.getService() != null) {
      target.setService(updatedQuestion.getService());
    }
    if (updatedQuestion.getId() != null) {
      target.setId(updatedQuestion.getId());
    }
    return serviceQuestionRepository.save(target);
  }

  // To update the description of a Question
  @PutMapping("api/service_question/{serviceQuestionId}/updateDescription/{description}")
  public ServiceQuestion updateDescriptionForAQuestion(
          @PathVariable("serviceQuestionId") Integer id,
          @PathVariable("description") String description
  )
  {
    serviceQuestionRepository.findServiceQuestionById(id).setDescription(description);
    return serviceQuestionRepository.findServiceQuestionById(id);
  }


  // to delete one question by given question id
  @DeleteMapping("api/service_question/{serviceQuestionId}")
  public void deleteOneAnswer( @PathVariable("serviceQuestionId") Integer id) {
    serviceQuestionRepository.deleteById(id);
  }

  @DeleteMapping("api/service_question/{serviceQuestionId}/service_question_answers/{serviceQuestionAnswerId}")
  public void deleteOneAnswerOfAQuestion(
          @PathVariable("serviceQuestionId") Integer qId,
          @PathVariable("serviceQuestionAnswerId") Integer aId
  )
  {
    ServiceQuestion target =
            serviceQuestionRepository.findServiceQuestionById(qId);
    target.removeServiceQuestionAnswer(aId);
  }

}
