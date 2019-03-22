package edu.neu.cs4500.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="frequently_asked_answers")
public class FrequentlyAskedAnswer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    @ManyToOne
    @JsonIgnore
    private FrequentlyAskedQuestion frequentlyAskedQuestion;
    @ManyToOne
    @JsonIgnore
    private User user;
    @Transient
    private String question;
    @Transient
    private String username;
    public String getQuestion() {
        return frequentlyAskedQuestion == null ? this.question : frequentlyAskedQuestion.getQuestion();
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getUsername() {
        return user == null ? this.username : user.getUsername();
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
        this.username = user.getUsername();
    }
    public FrequentlyAskedQuestion getFrequentlyAskedQuestion() {
        return frequentlyAskedQuestion;
    }
    public void setFrequentlyAskedQuestion(FrequentlyAskedQuestion frequentlyAskedQuestion) {
        this.frequentlyAskedQuestion = frequentlyAskedQuestion;
        this.question = frequentlyAskedQuestion.getQuestion();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}