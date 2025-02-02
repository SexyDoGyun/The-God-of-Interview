package capstone.interview.service;

import capstone.interview.model.*;
import capstone.interview.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalysisService {

    @Autowired
    private EmotionResultRepository emotionResultRepository;

    @Autowired
    private GazeResultRepository gazeResultRepository;

    @Autowired
    private HabitResultRepository habitResultRepository;

    @Autowired
    private AnalysisDataRepository analysisDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public AnalysisData saveAnalysisResults(Map<String, Object> analyzedData , Integer userId, String userName, String job, List<String> questions) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        User user = userOptional.get();

        AnalysisData analysisData = new AnalysisData();
        analysisData.setUser(user);
        analysisData.setUserName(userName);
        analysisData.setJob(job);
        analysisData.setInterviewDate(LocalDate.now());

        // analysisData를 먼저 저장하여 ID 생성
        AnalysisData savedAnalysisData = analysisDataRepository.save(analysisData);

        // 질문 리스트를 Question 엔티티로 변환하여 저장
        List<Question> questionEntities = questions.stream()
                .map(questionText -> {
                    Question question = new Question();
                    question.setQuestionText(questionText);
                    question.setAnalysisData(savedAnalysisData);  // 저장된 analysisData를 사용
                    return question;
                }).collect(Collectors.toList());

        // AnalysisData의 질문 리스트 설정
        savedAnalysisData.setQuestions(questionEntities);

        // 감정 분석 결과 저장
        Map<String, Map<String, Double>> emotionResults = (Map<String, Map<String, Double>>) analyzedData.get("emotion_results");
        saveEmotionResults(emotionResults, savedAnalysisData);

        // 시선 분석 결과 저장
        Map<String, Map<String, Double>> gazeResults = (Map<String, Map<String, Double>>) analyzedData.get("gaze_results");
        saveGazeResults(gazeResults, savedAnalysisData);

        // 습관어 분석 결과 저장
        Map<String, Map<String, Integer>> habitResults = (Map<String, Map<String, Integer>>) analyzedData.get("habit_results");
        saveHabitResults(habitResults, savedAnalysisData);

        return savedAnalysisData;
    }



    private void saveEmotionResults(Map<String, Map<String, Double>> emotionResults, AnalysisData analysisData) {
        for (Map.Entry<String, Map<String, Double>> entry : emotionResults.entrySet()) {
            EmotionResult emotionResult = new EmotionResult();
            emotionResult.setQuestion(entry.getKey());
            Map<String, Double> emotions = entry.getValue();

            emotionResult.setAngry(emotions.getOrDefault("angry", 0.0));
            emotionResult.setDisgust(emotions.getOrDefault("disgust", 0.0));
            emotionResult.setFear(emotions.getOrDefault("fear", 0.0));
            emotionResult.setHappy(emotions.getOrDefault("happy", 0.0));
            emotionResult.setNeutral(emotions.getOrDefault("neutral", 0.0));
            emotionResult.setSad(emotions.getOrDefault("sad", 0.0));
            emotionResult.setSurprise(emotions.getOrDefault("surprise", 0.0));

            emotionResult.setAnalysisData(analysisData);
            emotionResultRepository.save(emotionResult);
        }
    }

    private void saveGazeResults(Map<String, Map<String, Double>> gazeResults, AnalysisData analysisData) {
        for (Map.Entry<String, Map<String, Double>> entry : gazeResults.entrySet()) {
            GazeResult gazeResult = new GazeResult();
            gazeResult.setQuestion(entry.getKey());
            Map<String, Double> gaze = entry.getValue();

            gazeResult.setBlinking(gaze.getOrDefault("blinking", 0.0));
            gazeResult.setCenter(gaze.getOrDefault("center", 0.0));
            gazeResult.setLeftEye(gaze.getOrDefault("left_eye", 0.0));
            gazeResult.setRightEye(gaze.getOrDefault("right_eye", 0.0));

            gazeResult.setAnalysisData(analysisData);
            gazeResultRepository.save(gazeResult);
        }
    }

    private void saveHabitResults(Map<String, Map<String, Integer>> habitResults, AnalysisData analysisData) {
        for (Map.Entry<String, Map<String, Integer>> entry : habitResults.entrySet()) {
            HabitResult habitResult = new HabitResult();
            habitResult.setQuestion(entry.getKey());
            Map<String, Integer> habitWords = entry.getValue();

            habitResult.setWord1(habitWords.getOrDefault("그", 0));
            habitResult.setWord2(habitWords.getOrDefault("아", 0));
            habitResult.setWord3(habitWords.getOrDefault("음", 0));
            habitResult.setWord4(habitWords.getOrDefault("이런", 0));
            habitResult.setWord5(habitWords.getOrDefault("저", 0));
            habitResult.setWord6(habitWords.getOrDefault("저런", 0));

            habitResult.setAnalysisData(analysisData);
            habitResultRepository.save(habitResult);
        }
    }

}
