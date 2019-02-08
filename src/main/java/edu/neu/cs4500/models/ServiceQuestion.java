package edu.neu.cs4500.models;

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
    private ServiceQuestionType serviceQuestionType;

    @OneToMany(mappedBy="serviceQuestion")
    private List<ServiceQuestionAnswer> serviceQuestionAnswers;

    @ManyToOne
    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<ServiceQuestionAnswer> getServiceQuestionAnswers() {
        return serviceQuestionAnswers;
    }

    public void setServiceQuestionAnswers(List<ServiceQuestionAnswer> serviceQuestionAnswers) {
        this.serviceQuestionAnswers = serviceQuestionAnswers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setServiceQuestionType(ServiceQuestionType serviceQuestionType) {
        this.serviceQuestionType = serviceQuestionType;
    }
}