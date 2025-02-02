package capstone.interview.repository;

import capstone.interview.model.GazeResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GazeResultRepository extends JpaRepository<GazeResult, Long> {
    List<GazeResult> findByAnalysisDataId(Long analysisDataId);
}
