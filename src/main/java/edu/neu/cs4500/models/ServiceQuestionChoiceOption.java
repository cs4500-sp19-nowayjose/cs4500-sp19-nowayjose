package edu.neu.cs4500.models;

import javax.persistence.*;

@Entity
@Table(name="service_question_choice_options")
public class ServiceQuestionChoiceOption {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private ServiceQuestionAnswer questionAnswer;
    private String text;

    public ServiceQuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(ServiceQuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
