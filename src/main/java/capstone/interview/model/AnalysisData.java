package capstone.interview.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class AnalysisData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;  // 사용자 이름 저장
    private String job;       // 직업 정보 저장

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // User 엔터티와의 관계 설정

    @Column(name = "interview_date")
    private LocalDate interviewDate;

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    @OneToMany(mappedBy = "analysisData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions; // 질문 리스트 저장

    @OneToMany(mappedBy = "analysisData", cascade = CascadeType.ALL)
    private List<EmotionResult> emotionResults;

    @OneToMany(mappedBy = "analysisData", cascade = CascadeType.ALL)
    private List<GazeResult> gazeResults;

    @OneToMany(mappedBy = "analysisData", cascade = CascadeType.ALL)
    private List<HabitResult> habitResults;

    // Getter and Setter for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for job
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    // Getter and Setter for questions
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EmotionResult> getEmotionResults() {
        return emotionResults;
    }

    public void setEmotionResults(List<EmotionResult> emotionResults) {
        this.emotionResults = emotionResults;
    }

    public List<GazeResult> getGazeResults() {
        return gazeResults;
    }

    public void setGazeResults(List<GazeResult> gazeResults) {
        this.gazeResults = gazeResults;
    }

    public List<HabitResult> getHabitResults() {
        return habitResults;
    }

    public void setHabitResults(List<HabitResult> habitResults) {
        this.habitResults = habitResults;
    }

    // Getter and Setter for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
