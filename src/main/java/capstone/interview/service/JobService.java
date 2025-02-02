package capstone.interview.service;

import capstone.interview.model.Job;
import capstone.interview.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> findByIndustryName(String industryName) {
        return jobRepository.findByIndustryName(industryName);
    }
}