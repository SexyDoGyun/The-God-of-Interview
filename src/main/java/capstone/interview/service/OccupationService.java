package capstone.interview.service;

import capstone.interview.model.Occupation;
import capstone.interview.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationService {
    @Autowired
    private OccupationRepository occupationRepository;

    public List<Occupation> findByJobName(String jobName) {
        return occupationRepository.findByJobName(jobName);
    }
}
