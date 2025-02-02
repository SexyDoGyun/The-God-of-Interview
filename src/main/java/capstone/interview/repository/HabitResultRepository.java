package capstone.interview.repository;

import capstone.interview.model.HabitResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitResultRepository extends JpaRepository<HabitResult, Long> {
    List<HabitResult> findByAnalysisDataId(Long analysisDataId);
}
