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
        List<ScoredUser> scoredUsers = calculateUsersMatchScores(users);
        sortUsers(scoredUsers);
        List<User> sortedUsers = filterSortedUsers(scoredUsers);
        return sortedUsers;
    }

    List<ScoredUser> calculateUsersMatchScores(List<User> users) {
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
            scoredUsers.add(sc);
        }
        return scoredUsers;
    }

    // Modifies the `scoredUsers` by sorting in place.
    void sortUsers(List<ScoredUser> scoredUsers) {
        scoredUsers.sort((o1, o2) -> {
            if (o1.score > o2.score) return -1;
            else if (o2.score > o1.score) return 1;
            else return o1.user.getUsername().compareTo(o2.user.getUsername());
        });
    }

    List<User> filterSortedUsers(List<ScoredUser> scoredUsers) {
        ArrayList<User> sortedUsers = new ArrayList<>();
        for (ScoredUser sc : scoredUsers) {
            if (sc.score == 0) break;
            sortedUsers.add(sc.user);
        }
        return sortedUsers;
    }


    static class ScoredUser {
        User user;
        Integer score;
        ScoredUser(User user, Integer score) {
            this.user = user;
            this.score = score;
        }

        @Override
        public int hashCode() {
            return ("" + user.hashCode() + ":" + score.hashCode()).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ScoredUser) {
                ScoredUser sc = (ScoredUser) obj;
                return sc.user.equals(user) && sc.score.equals(score);
            } else {
                return false;
            }
        }
    }

    public static class QuestionAnswerCriterion {

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
            if (trueFalseAnswer.isPresent()
                    && answer.getTrueFalseAnswer() != null
                    && trueFalseAnswer.get().equals(answer.getTrueFalseAnswer())) {
                score += 1;
            }
            if (rangeAnswer.isPresent()
                    && answer.getMaxRangeAnswer() != null
                    && answer.getMinRangeAnswer() != null
                    && rangeAnswer.get() <= answer.getMaxRangeAnswer()
                    && rangeAnswer.get() >= answer.getMinRangeAnswer()
            ) {
                score += 1;
            }
            if (choiceAnswer.isPresent()
                    && answer.getChoiceAnswer() != null
                    && choiceAnswer.get().equals(answer.getChoiceAnswer())) {
                score += 1;
            }
            return score;
        }

    }

}
