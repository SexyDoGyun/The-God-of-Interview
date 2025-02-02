package capstone.interview.model;

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
public class EmotionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question; // 질문 번호 혹은 ID

    private Double angry;
    private Double disgust;
    private Double fear;
    private Double happy;
    private Double neutral;
    private Double sad;
    private Double surprise;

    @ManyToOne
    @JoinColumn(name = "analysis_data_id") // AnalysisData와 연관된 경우
    private AnalysisData analysisData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Double getAngry() {
        return angry;
    }

    public void setAngry(Double angry) {
        this.angry = angry;
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

    public Double getFear() {
        return fear;
    }

    public void setFear(Double fear) {
        this.fear = fear;
    }

    public Double getHappy() {
        return happy;
    }

    public void setHappy(Double happy) {
        this.happy = happy;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }

    public Double getSad() {
        return sad;
    }

    public void setSad(Double sad) {
        this.sad = sad;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public AnalysisData getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(AnalysisData analysisData) {
        this.analysisData = analysisData;
    }
}

