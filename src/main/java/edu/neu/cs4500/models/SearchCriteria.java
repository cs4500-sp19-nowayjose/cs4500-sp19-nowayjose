package edu.neu.cs4500.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SearchCriteria {

    private Map<ServiceQuestion, QuestionAnswerCriterion> criteria;

    public SearchCriteria(Map<ServiceQuestion, QuestionAnswerCriterion> criteria) {
        this.criteria = criteria;
    }

    public List<User> orderUsersByScore(List<User> users) {
        ArrayList<ScoredUser> scoredUsers = new ArrayList<>();
        for (User user : users) {
            ScoredUser sc = new ScoredUser(user, 0);
            for (ServiceQuestionAnswer answer : user.getServiceQuestionAnswers()) {
                if (criteria.containsKey(answer.getServiceQuestion())) {
                    sc.score += criteria
                        .get(answer.getServiceQuestion())
                        .scoreAnswerMatch(answer);
                }
            }
        }
        scoredUsers.sort((o1, o2) -> {
            if (o1.score > o2.score) return 1;
            else if (o2.score > o1.score) return -1;
            else return 0;
        });
        ArrayList<User> sortedUsers = new ArrayList<>();
        for (ScoredUser sc : scoredUsers) {
            sortedUsers.add(sc.user);
        }
        return sortedUsers;
    }

    private class ScoredUser {
        User user;
        Integer score;
        ScoredUser(User user, Integer score) {
            this.user = user;
            this.score = score;
        }
    }

    public class QuestionAnswerCriterion {

        private Optional<Boolean> trueFalseAnswer;
        private Optional<Integer> rangeAnswer;
        private Optional<Integer> choiceAnswer;

        public QuestionAnswerCriterion(Optional<Boolean> trueFalseAnswer, Optional<Integer> rangeAnswer, Optional<Integer> choiceAnswer) {
            this.trueFalseAnswer = trueFalseAnswer;
            this.rangeAnswer = rangeAnswer;
            this.choiceAnswer = choiceAnswer;
        }

        public int scoreAnswerMatch(ServiceQuestionAnswer answer) {
            int score = 0;
            if (trueFalseAnswer.isPresent() && trueFalseAnswer.get().equals(answer.getTrueFalseAnswer())) {
                score += 1;
            }
            if (rangeAnswer.isPresent()
                && rangeAnswer.get() < answer.getMaxRangeAnswer()
                && rangeAnswer.get() > answer.getMinRangeAnswer()
            ) {
                score += 1;
            }
            if (choiceAnswer.isPresent() && choiceAnswer.get().equals(answer.getChoiceAnswer())) {
                score += 1;
            }
            return score;
        }

    }

}
