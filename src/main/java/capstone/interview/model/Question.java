package capstone.interview.model;

import jakarta.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;  // 질문 내용

    @ManyToOne
    @JoinColumn(name = "analysis_data_id")  // 외래 키로 AnalysisData와 연결
    private AnalysisData analysisData;

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public AnalysisData getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(AnalysisData analysisData) {
        this.analysisData = analysisData;
    }
}
