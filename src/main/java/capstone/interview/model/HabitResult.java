package capstone.interview.model;

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
public class HabitResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question; // 질문 번호 혹은 ID

    private Integer word1; // 예: "그" 사용 빈도
    private Integer word2; // 예: "아" 사용 빈도
    private Integer word3; // 예: "음" 사용 빈도
    private Integer word4; // 예: "이런" 사용 빈도
    private Integer word5; // 예: "저" 사용 빈도
    private Integer word6; // 예: "저런" 사용 빈도

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

    public Integer getWord1() {
        return word1;
    }

    public void setWord1(Integer word1) {
        this.word1 = word1;
    }

    public Integer getWord2() {
        return word2;
    }

    public void setWord2(Integer word2) {
        this.word2 = word2;
    }

    public Integer getWord3() {
        return word3;
    }

    public void setWord3(Integer word3) {
        this.word3 = word3;
    }

    public Integer getWord4() {
        return word4;
    }

    public void setWord4(Integer word4) {
        this.word4 = word4;
    }

    public Integer getWord5() {
        return word5;
    }

    public void setWord5(Integer word5) {
        this.word5 = word5;
    }

    public Integer getWord6() {
        return word6;
    }

    public void setWord6(Integer word6) {
        this.word6 = word6;
    }

    public AnalysisData getAnalysisData() {
        return analysisData;
    }

    public void setAnalysisData(AnalysisData analysisData) {
        this.analysisData = analysisData;
    }
}

