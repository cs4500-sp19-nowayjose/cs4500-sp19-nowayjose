package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="service_question_answers")
public class ServiceQuestionAnswer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Boolean trueFalseAnswer;
    private Integer maxRangeAnswer;
    private Integer minRangeAnswer;
    private Integer choiceAnswer;

    @OneToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ServiceQuestion serviceQuestion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTrueFalseAnswer() {
        return trueFalseAnswer;
    }

    public void setTrueFalseAnswer(Boolean trueFalseAnswer) {
        this.trueFalseAnswer = trueFalseAnswer;
    }

    public Integer getMaxRangeAnswer() {
        return maxRangeAnswer;
    }

    public void setMaxRangeAnswer(Integer maxRangeAnswer) {
        this.maxRangeAnswer = maxRangeAnswer;
    }

    public Integer getMinRangeAnswer() {
        return minRangeAnswer;
    }

    public void setMinRangeAnswer(Integer minRangeAnswer) {
        this.minRangeAnswer = minRangeAnswer;
    }

    public Integer getChoiceAnswer() {
        return choiceAnswer;
    }

    public void setChoiceAnswer(Integer choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceQuestion getServiceQuestion() {
        return serviceQuestion;
    }

    public void setServiceQuestion(ServiceQuestion serviceQuestion) {
        this.serviceQuestion = serviceQuestion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
