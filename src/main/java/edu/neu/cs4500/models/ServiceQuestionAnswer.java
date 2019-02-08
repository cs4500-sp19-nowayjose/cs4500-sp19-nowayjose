package edu.neu.cs4500.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="answers")
public class ServiceQuestionAnswer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String body;

    @OneToOne
    @MapsId
    private User updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    private ServiceQuestion serviceQuestion;

    public ServiceQuestion getServiceQuestion() {
        return serviceQuestion;
    }

    public void setServiceQuestion(ServiceQuestion serviceQuestion) {
        this.serviceQuestion = serviceQuestion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
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
