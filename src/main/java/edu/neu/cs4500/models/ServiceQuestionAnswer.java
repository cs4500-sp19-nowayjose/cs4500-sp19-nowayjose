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
    // In JSON format, include a "provider" field with just the ServiceProvider's ID
    // instead of ignoring it or including the full nested object
    // EX. { provider: { id: 123 } }
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ServiceProvider provider;

    @ManyToOne
    // In JSON format, include a "serviceQuestion" field with just the question's ID
    // instead of ignoring it or including the full nested object
    // EX. { serviceQuestion: { id: 3 } }
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

    public ServiceQuestionAnswer setId(Integer id) {
        this.id = id;
        return this;
    }

    public Boolean getTrueFalseAnswer() {
        return trueFalseAnswer;
    }

    public ServiceQuestionAnswer setTrueFalseAnswer(Boolean trueFalseAnswer) {
        this.trueFalseAnswer = trueFalseAnswer;
        return this;
    }

    public Integer getMaxRangeAnswer() {
        return maxRangeAnswer;
    }

    public ServiceQuestionAnswer setMaxRangeAnswer(Integer maxRangeAnswer) {
        this.maxRangeAnswer = maxRangeAnswer;
        return this;
    }

    public Integer getMinRangeAnswer() {
        return minRangeAnswer;
    }

    public ServiceQuestionAnswer setMinRangeAnswer(Integer minRangeAnswer) {
        this.minRangeAnswer = minRangeAnswer;
        return this;
    }

    public Integer getChoiceAnswer() {
        return choiceAnswer;
    }

    public ServiceQuestionAnswer setChoiceAnswer(Integer choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
        return this;
    }

    public ServiceProvider getProvider() {
        return provider;
    }

    public ServiceQuestionAnswer setProvider(ServiceProvider provider) {
        this.provider = provider;
        return this;
    }

    public ServiceQuestion getServiceQuestion() {
        return serviceQuestion;
    }

    public ServiceQuestionAnswer setServiceQuestion(ServiceQuestion serviceQuestion) {
        this.serviceQuestion = serviceQuestion;
        return this;
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
