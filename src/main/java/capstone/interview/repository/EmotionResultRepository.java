package capstone.interview.repository;

import capstone.interview.model.EmotionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmotionResultRepository extends JpaRepository<EmotionResult, Long> {
    List<EmotionResult> findByAnalysisDataId(Long analysisDataId);
}