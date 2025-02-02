package capstone.interview.repository;

import capstone.interview.model.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
    List<Occupation> findByJobName(String jobName);
}
