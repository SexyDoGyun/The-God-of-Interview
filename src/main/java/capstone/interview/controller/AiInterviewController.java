package capstone.interview.controller;

import capstone.interview.model.*;
import capstone.interview.repository.*;
import capstone.interview.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AiInterviewController {
    @Autowired
    private IndustryService industryService;

    @Autowired
    private JobService jobService;

    @Autowired
    private OccupationService occupationService;

    @Autowired
    private InterviewQuestionService interviewQuestionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnalysisDataRepository analysisDataRepository;

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping("/Ai-interview-step1")
    public String aiInterviewStep1(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        return "Ai-interview-step1";
    }

    @GetMapping("/Ai-interview-step2")
    public String aiInterviewStep2(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        model.addAttribute("industries", industryService.findAll());
        return "Ai-interview-step2";
    }

    @GetMapping("/industries")
    @ResponseBody
    public List<Industry> getIndustries() {
        return industryService.findAll();
    }

    @GetMapping("/jobs")
    @ResponseBody
    public List<Job> getJobsByIndustry(@RequestParam("industryName") String industryName) {
        return jobService.findByIndustryName(industryName);
    }

    @GetMapping("/occupations")
    @ResponseBody
    public List<Occupation> getOccupationsByJob(@RequestParam("jobName") String jobName) {
        return occupationService.findByJobName(jobName);
    }

    @GetMapping("/interview-questions")
    @ResponseBody
    public List<InterviewQuestion> getInterviewQuestions(@RequestParam("occupationName") String occupationName) {
        return interviewQuestionService.getQuestionsByOccupationName(occupationName);
    }

    @GetMapping("/Ai-interview-step3")
    public String aiInterviewStep3(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam("occupationName") String occupationName,
                                   Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        userOptional.ifPresent(user -> {
            model.addAttribute("username", user.getName()); // 사용자 이름 추가
            model.addAttribute("user_id", user.getId());    // user_id 추가
        });

        model.addAttribute("occupationName", occupationName);
        return "Ai-interview-step3";
    }



    @GetMapping("/Ai-interview-step4")
    public String aiInterviewStep4(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Optional<AnalysisData> recentAnalysis = analysisDataRepository.findTopByOrderByIdDesc();
        if (recentAnalysis.isPresent()) {
            AnalysisData analysisData = recentAnalysis.get();
            Map<String, String> feedback = feedbackService.generateFeedback(analysisData);
            model.addAllAttributes(feedback);
            model.addAttribute("userName", analysisData.getUserName());
            model.addAttribute("job", analysisData.getJob());
        } else {
            model.addAttribute("userName", "Unknown");
            model.addAttribute("job", "Unknown");
        }
        return "Ai-interview-step4";
    }


}
