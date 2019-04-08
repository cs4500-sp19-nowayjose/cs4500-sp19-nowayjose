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

    public List<ServiceProvider> orderAndFilterProvidersByScore(List<ServiceProvider> providers) {
        List<ScoredProvider> scoredProviders = calculateProvidersMatchScores(providers);
        sortProviders(scoredProviders);
        return filterSortedProviders(scoredProviders);
    }

    List<ScoredProvider> calculateProvidersMatchScores(List<ServiceProvider> providers) {
        ArrayList<ScoredProvider> scoredProviders = new ArrayList<>();
        for (ServiceProvider provider : providers) {
            ScoredProvider sc = new ScoredProvider(provider, 0);
            for (ServiceQuestionAnswer answer : provider.getServiceQuestionAnswers()) {
                if (criteria.containsKey(answer.getServiceQuestion())) {
                    sc.score += criteria
                        .get(answer.getServiceQuestion())
                        .scoreAnswerMatch(answer);
                }
            }
            scoredProviders.add(sc);
        }
        return scoredProviders;
    }

    // Modifies the `scoredProviders` by sorting in place.
    void sortProviders(List<ScoredProvider> scoredProviders) {
        scoredProviders.sort((o1, o2) -> {
            if (o1.score > o2.score) return -1;
            else if (o2.score > o1.score) return 1;
            else return o1.provider.getTitle().compareTo(o2.provider.getTitle());
        });
    }

    List<ServiceProvider> filterSortedProviders(List<ScoredProvider> scoredProviders) {
        ArrayList<ServiceProvider> sortedProviders = new ArrayList<>();
        for (ScoredProvider sc : scoredProviders) {
            if (sc.score == 0) break;
            sortedProviders.add(sc.provider);
        }
        return sortedProviders;
    }


    static class ScoredProvider {
        ServiceProvider provider;
        Integer score;
        ScoredProvider(ServiceProvider provider, Integer score) {
            this.provider = provider;
            this.score = score;
        }

        @Override
        public int hashCode() {
            return ("" + provider.hashCode() + ":" + score.hashCode()).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ScoredProvider) {
                ScoredProvider sc = (ScoredProvider) obj;
                return sc.provider.equals(provider) && sc.score.equals(score);
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
