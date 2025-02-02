package capstone.interview.repository;

import capstone.interview.model.AnalysisData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnalysisDataRepository extends JpaRepository<AnalysisData, Long> {
    Optional<AnalysisData> findTopByOrderByIdDesc();

    List<AnalysisData> findByUserId(Long userId);
}




