package edu.neu.cs4500.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="service_questions")
public class ServiceQuestion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @JsonInclude
    private ServiceQuestionType serviceQuestionType;

    @OneToMany(mappedBy="serviceQuestion")
    @JsonIgnore
    private List<ServiceQuestionAnswer> serviceQuestionAnswers;

    @OneToMany(mappedBy="serviceQuestion")
    private List<ServiceQuestionChoiceOption> serviceQuestionChoiceOptions;

    @ManyToOne
    @JsonIgnore
    private Service service;

    // Need an explicit default now
    public ServiceQuestion() {
        super();
    }

    // Allows mapping a JSON request with `{"id": id}` to as the serviceQuestion field
    // to a ServiceQuestion with that id.
    @JsonCreator
    public ServiceQuestion(@JsonProperty("id") int id) {
        super();
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<ServiceQuestionAnswer> getServiceQuestionAnswers() {
        return serviceQuestionAnswers;
    }

    public Integer getId() {
        return id;
    }

    public ServiceQuestion setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ServiceQuestion setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ServiceQuestion setDescription(String description) {
        this.description = description;
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

    public ServiceQuestionType getServiceQuestionType() {
        return serviceQuestionType;
    }

    public ServiceQuestion setServiceQuestionType(ServiceQuestionType serviceQuestionType) {
        this.serviceQuestionType = serviceQuestionType;
        return this;
    }

    public List<ServiceQuestionChoiceOption> getServiceQuestionChoiceOptions() {
        return serviceQuestionChoiceOptions;
    }

    public void addServiceQuestionAnswer(ServiceQuestionAnswer ans){
        this.serviceQuestionAnswers.add(ans);
    }

    public void setServiceQuestionAnswers(List<ServiceQuestionAnswer> answers) {
        this.serviceQuestionAnswers = answers;
    }

    public void removeServiceQuestionAnswer(Integer ansId){
        for (ServiceQuestionAnswer ans: serviceQuestionAnswers){
            if (ans.getId() == ansId){
                serviceQuestionAnswers.remove(ans);
            }
        }
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceQuestion) {
            ServiceQuestion question = (ServiceQuestion) obj;
            return question.getId().equals(this.id);
        } else {
            return false;
        }
    }
}