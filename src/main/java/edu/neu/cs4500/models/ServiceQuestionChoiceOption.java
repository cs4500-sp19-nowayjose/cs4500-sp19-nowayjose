package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="service_question_choice_options")
public class ServiceQuestionChoiceOption {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JsonIgnore
    private ServiceQuestion serviceQuestion;
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceQuestion getServiceQuestion() {
        return serviceQuestion;
    }

    public void setServiceQuestion(ServiceQuestion serviceQuestion) {
        this.serviceQuestion = serviceQuestion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
