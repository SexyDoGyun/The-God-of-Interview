package capstone.interview.model;

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
public class GazeResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question; // 질문 번호 혹은 ID

    private Double blinking;
    private Double center;
    private Double leftEye;
    private Double rightEye;

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

    public Double getBlinking() {
        return blinking;
    }

    public void setBlinking(Double blinking) {
        this.blinking = blinking;
    }

    public Double getCenter() {
        return center;
    }

    public void setCenter(Double center) {
        this.center = center;
    }

    public Double getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(Double leftEye) {
        this.leftEye = leftEye;
    }

    public Double getRightEye() {
        return rightEye;
    }

    public void setRightEye(Double rightEye) {
        this.rightEye = rightEye;
    }

    public AnalysisData getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(AnalysisData analysisData) {
        this.analysisData = analysisData;
    }
}

