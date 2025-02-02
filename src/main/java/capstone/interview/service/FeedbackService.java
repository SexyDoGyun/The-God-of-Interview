package capstone.interview.service;

import capstone.interview.model.AnalysisData;
import capstone.interview.model.EmotionResult;
import capstone.interview.model.GazeResult;
import capstone.interview.model.HabitResult;
import capstone.interview.model.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FeedbackService {

    public Map<String, String> generateFeedback(AnalysisData analysisData) {
        Map<String, String> feedback = new HashMap<>();

        // questions를 List<String>으로 변환 (questionText 필드 사용)
        List<String> questionTexts = analysisData.getQuestions().stream()
                .map(Question::getQuestionText) // Question 객체에서 questionText 필드 추출
                .collect(Collectors.toList());

        // 감정 분석 피드백 생성
        Map<String, String> emotionFeedback = calculateEmotionFeedback(analysisData);
        feedback.put("emotionRating", emotionFeedback.get("rating"));
        feedback.put("emotionSummary", emotionFeedback.get("summary"));
        feedback.put("emotionEncouragement", emotionFeedback.get("encouragement"));
        feedback.put("emotionPracticeNeeded", String.join(", ", getPracticeQuestions(questionTexts, emotionFeedback.get("practiceNeeded"))));

        // 시선 분석 피드백 생성
        Map<String, String> gazeFeedback = calculateGazeFeedback(analysisData);
        feedback.put("gazeRating", gazeFeedback.get("rating"));
        feedback.put("gazeSummary", gazeFeedback.get("summary"));
        feedback.put("gazeEncouragement", gazeFeedback.get("encouragement"));
        feedback.put("gazePracticeNeeded", String.join(", ", getPracticeQuestions(questionTexts, gazeFeedback.get("practiceNeeded"))));

        // 습관어 분석 피드백 생성
        Map<String, String> habitFeedback = calculateHabitFeedback(analysisData);
        feedback.put("habitRating", habitFeedback.get("rating"));
        feedback.put("habitTopWords", habitFeedback.get("topWords"));
        feedback.put("habitSummary", habitFeedback.get("summary"));
        feedback.put("habitEncouragement", habitFeedback.get("encouragement"));

        return feedback;
    }


    private Map<String, String> calculateEmotionFeedback(AnalysisData analysisData) {
        List<EmotionResult> emotionResults = analysisData.getEmotionResults();
        double sumNotHappy = 0;
        double sumHappy = 0;
        List<Double> scores = new ArrayList<>();

        for (EmotionResult emotionResult : emotionResults) {
            double notHappy = emotionResult.getAngry() + emotionResult.getDisgust() + emotionResult.getFear()
                    + emotionResult.getSad() + emotionResult.getSurprise() + (emotionResult.getNeutral() / 2);
            double happy = emotionResult.getHappy();
            scores.add(happy - notHappy); // 감정 점수를 계산하여 리스트에 추가
            sumNotHappy += notHappy;
            sumHappy += happy;
        }

        double emotionRatio = sumHappy / (sumNotHappy + sumHappy);
        String rating;
        String summary;
        String encouragement;

        if (emotionRatio > 0.6) {
            rating = "상";
            summary = "밝은 표정을 잘 유지하여, 면접을 잘 마무리하였습니다! 이대로 진행시, 면접에서도 좋은 평가와 함께 좋은 인상을 남길 수 있을 것 입니다. 반복적인 연습을 통해 낯선 문항들은 없는지 연습해보세요! 더 좋은 결과를 얻을 수 있을 것 입니다.";
            encouragement = "🌈 아주 잘했어요!! 😁";
        } else if (emotionRatio < 0.3) {
            rating = "하";
            summary = "면접 내내 좋은 표정을 유지하지 못하였습니다...다소 아쉬운 결과이지만, 여러번의 연습을 진행한다면 보다 더 밝은 표정을 면접동안 유지할 수 있을 것 입니다. 특히 어두운 표정은 자신감, 불안, 걱정과 같은 요소들을 간접적으로 면접관에게 전달합니다. 이러한 점을 생각하며 연습을 진행하며 화면에 보이는 자신의 표정을 보고 긍정적으로 평가할 수 있을지 스스로 고민을 진행해주세요!";
            encouragement = "💧 다소 아쉬워요...😢";
        } else {
            rating = "중";
            summary = "면접을 잘 진행하였지만 밝은 표정이 제대로 유지되지 않은 문항들이 있습니다. 문항을 확인하고 본인의 표정이 어떠했을지 다시 떠올려보세요. 반복적인 연습을 통해 낯선 문항들은 없는지 연습해보세요! 낯선 문항들을 더 자주 만나 당황스럽지 않은지, 밝게 유지하는지 점검하며 계속해서 연습해보세요!";
            encouragement = "🔥 조금만 더 분발해요!! 😤";
        }

        String practiceNeeded = findPracticeNeededIndices(scores);

        Map<String, String> feedback = new HashMap<>();
        feedback.put("rating", rating);
        feedback.put("summary", summary);
        feedback.put("encouragement", encouragement);
        feedback.put("practiceNeeded", practiceNeeded);
        return feedback;
    }

    private Map<String, String> calculateGazeFeedback(AnalysisData analysisData) {
        List<GazeResult> gazeResults = analysisData.getGazeResults();

        double totalBlinking = 0;
        double totalRight = 0;
        double totalLeft = 0;
        double totalCenter = 0;
        List<Double> scores = new ArrayList<>();

        for (GazeResult gazeResult : gazeResults) {
            double score = gazeResult.getCenter() - (gazeResult.getLeftEye() + gazeResult.getRightEye());
            scores.add(score);
            totalBlinking += gazeResult.getBlinking();
            totalRight += gazeResult.getRightEye();
            totalLeft += gazeResult.getLeftEye();
            totalCenter += gazeResult.getCenter();
        }

        double gazeRatio = (totalLeft + totalRight) / (totalBlinking + totalRight + totalLeft + totalCenter);
        String rating;
        String summary;
        String encouragement;

        if (gazeRatio <= 0.2) {
            rating = "상";
            summary = "중앙을 잘 응시하며, 면접을 잘 마무리하였습니다! 이대로 진행시, 면접에서도 좋은 평가와 함께 좋은 인상을 남길 수 있을 것 입니다. 다른 곳을 응시하지 않고 가운데를 응시할 수 있도록 이대로만 연습해주세요!";
            encouragement = "🌈 아주 잘했어요!! 😁";
        } else if (gazeRatio >= 0.3) {
            rating = "하";
            summary = "면접 진행 중 중앙을 바라보지 않고 다른 곳을 쳐다본 경우가 많았습니다. 면접 문항들을 확인한 후, 본인이 느끼기에 답변을 어떻게 진행했어야 했을지 고민해주세요. 특히 긴장을 하거나 답변이 잘 정리되지 않았다면 중앙을 응시하지 않을 가능성이 높아집니다. 어떤 문항이 당황스러웠는지 확인하고 생각 시간 내에 빠르게 답변을 정리한 후, 중앙을 잘 유지할 수 있도록 거듭 연습을 진행한다면 더 좋은 결과를 받을 수 있을 것입니다!";
            encouragement = "💧 다소 아쉬워요...😢";
        } else {
            rating = "중";
            summary = "중앙을 잘 응시하였지만, 다른 곳을 응시한 경우가 다소 발견되었습니다. 면접 문항들을 확인하며 본인이 답변 도중 어떤 식으로 자신감있게 응시하였는지 떠올려보세요. 여러번 연습을 진행한 후에는 더 자신감있게 중앙을 잘 응시하여 면접을 진행할 수 있을 것입니다. 답변을 말하는 도중 이외에도 면접관을 응시하는 것은 경청, 존중의 표시이기 떄문에 중앙을 잘 응시할 수 있도록 조금 더 노력해주세요!";
            encouragement = "🔥 조금만 더 분발해요!! 😤";
        }

        String practiceNeeded = findPracticeNeededIndices(scores);

        Map<String, String> feedback = new HashMap<>();
        feedback.put("rating", rating);
        feedback.put("summary", summary);
        feedback.put("encouragement", encouragement);
        feedback.put("practiceNeeded", practiceNeeded);
        return feedback;
    }

    private String findPracticeNeededIndices(List<Double> scores) {
        return IntStream.range(0, scores.size())
                .boxed()
                .sorted((i, j) -> Double.compare(scores.get(i), scores.get(j)))
                .limit(2)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private List<String> getPracticeQuestions(List<String> questions, String practiceNeededIndices) {
        List<Integer> indices = Arrays.stream(practiceNeededIndices.split(", "))
                .map(Integer::parseInt)
                .limit(2)
                .collect(Collectors.toList());

        return indices.stream()
                .map(index -> "질문" + (index + 1) + ": " + questions.get(index)) // 인덱스를 사용해 질문 번호와 내용 함께 표시
                .collect(Collectors.toList());
    }




    // 습관어 분석 피드백 생성
    private Map<String, String> calculateHabitFeedback(AnalysisData analysisData) {
        List<HabitResult> habitResults = analysisData.getHabitResults();
        Map<String, Integer> habitWords = new HashMap<>();

        for (HabitResult habitResult : habitResults) {
            habitWords.put("그", habitWords.getOrDefault("그", 0) + (habitResult.getWord1() != null ? habitResult.getWord1() : 0));
            habitWords.put("아", habitWords.getOrDefault("아", 0) + (habitResult.getWord2() != null ? habitResult.getWord2() : 0));
            habitWords.put("음", habitWords.getOrDefault("음", 0) + (habitResult.getWord3() != null ? habitResult.getWord3() : 0));
            habitWords.put("이런", habitWords.getOrDefault("이런", 0) + (habitResult.getWord4() != null ? habitResult.getWord4() : 0));
            habitWords.put("저", habitWords.getOrDefault("저", 0) + (habitResult.getWord5() != null ? habitResult.getWord5() : 0));
            habitWords.put("저런", habitWords.getOrDefault("저런", 0) + (habitResult.getWord6() != null ? habitResult.getWord6() : 0));
        }

        int totalHabitWordCount = habitWords.values().stream().mapToInt(Integer::intValue).sum();
        List<Map.Entry<String, Integer>> topHabitWords = habitWords.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .collect(Collectors.toList());

        String rating;
        String summary;
        String encouragement;
        if (totalHabitWordCount < 5) {
            rating = "상";
            summary = "전반적으로 습관어 사용 빈도 수가 낮습니다! 이대로 답변을 진행할 수 있다면 면접에서도 긴장한 티나 당황한 티가 잘 드러나지 않을 확률이 높습니다. 여러 연습을 진행해보면서 질문에 대한 답변을 빠르게 구상하여 습관어가 발생하지 않을 수 있도록 조심해주세요!";
            encouragement = "🌈 아주 잘했어요!! 😁";
        } else if (totalHabitWordCount <= 15) {
            rating = "중";
            summary = "많은 습관어를 사용하지는 않았지만, 높은 평가를 위해서는 조금 더 노력해야합니다! 습관어는 답변에 대한 신뢰감을 나타내기도 합니다. 따라서 당황했던 질문이 무엇이었는지 떠올려 질문에 대한 답변을 어떻게 구상해야할지 고민해보세요. 여러번 연습을 진행한다면, 분명히 더 좋은 평가를 얻을 수 있을 것입니다.";
            encouragement = "💧 다소 아쉬워요...😢";
        } else {
            rating = "하";
            summary = "습관어 사용 빈도 수가 매우 많습니다. 이는 면접관에게 긴장한 티나 당황한 티가 잘 드러날 수 있는 수치입니다. 특히 반복되는 습관어 사용은 답변의 요지가 흔들려 신뢰감이 없어보이는 평가를 받을 수 있습니다. 개선을 위해서는 반복적인 연습을 통해 정확한 답변만을 말할 수 있는 연습이 필요합니다.";
            encouragement = "🔥 조금만 더 분발해요!! 😤";
        }

        StringBuilder topWordsMessage = new StringBuilder();
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topHabitWords) {
            topWordsMessage.append(rank).append("위: '").append(entry.getKey()).append("' - ").append(entry.getValue()).append("회, ");
            rank++;
        }

// 마지막 쉼표와 공백 제거
        if (topWordsMessage.length() > 0) {
            topWordsMessage.setLength(topWordsMessage.length() - 2);
        }


        Map<String, String> feedback = new HashMap<>();
        feedback.put("rating", rating);
        feedback.put("topWords", topWordsMessage.toString());
        feedback.put("summary", summary);
        feedback.put("encouragement", encouragement);
        return feedback;
    }
}


