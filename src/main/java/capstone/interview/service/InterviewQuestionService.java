package capstone.interview.service;

import capstone.interview.model.InterviewQuestion;
import capstone.interview.repository.InterviewQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewQuestionService {
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;

    public List<InterviewQuestion> getQuestionsByOccupationName(String occupationName) {
        return interviewQuestionRepository.findByOccupationName(occupationName);
    }
}
