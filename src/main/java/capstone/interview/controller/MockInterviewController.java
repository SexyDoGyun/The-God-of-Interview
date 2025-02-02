package capstone.interview.controller;

import capstone.interview.model.AnalysisData;
import capstone.interview.model.User;
import capstone.interview.repository.AnalysisDataRepository;
import capstone.interview.repository.UserRepository;
import capstone.interview.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MockInterviewController {

    @Autowired
    private AnalysisDataRepository analysisDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/mockinterview")
    public String viewMockInterviews(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            model.addAttribute("isAuthenticated", false);  // 인증되지 않음
            return "redirect:/login";
        }

        // 현재 로그인한 사용자 가져오기
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            model.addAttribute("isAuthenticated", false);  // 인증되지 않음
            return "redirect:/login";
        }

        model.addAttribute("isAuthenticated", true);  // 인증됨

        // 사용자의 모든 면접 기록 가져오기
        List<AnalysisData> interviewRecords = analysisDataRepository.findByUserId(user.getId());
        model.addAttribute("interviewRecords", interviewRecords);

        return "mockinterview";  // mockinterview.html로 이동
    }


    @GetMapping("/mockinterview/details/{id}")
    public String viewMockInterviewDetail(@PathVariable Long id, Model model) {
        // ID에 해당하는 면접 기록 가져오기
        Optional<AnalysisData> analysisDataOptional = analysisDataRepository.findById(id);
        if (analysisDataOptional.isPresent()) {
            AnalysisData analysisData = analysisDataOptional.get();
            Map<String, String> feedback = feedbackService.generateFeedback(analysisData);  // 피드백 생성

            model.addAllAttributes(feedback);  // 피드백 데이터 모델에 추가
            model.addAttribute("userName", analysisData.getUserName());
            model.addAttribute("job", analysisData.getJob());
            model.addAttribute("interviewDate", analysisData.getInterviewDate());

            return "Ai-interview-Feedback";  // Ai-interview-Feedback.html로 이동
        } else {
            return "redirect:/mockinterview";  // 기록이 없으면 mockinterview 페이지로 리다이렉트
        }
    }



}
