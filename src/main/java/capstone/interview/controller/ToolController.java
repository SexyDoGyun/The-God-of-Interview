package capstone.interview.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToolController {

    @GetMapping("/character_count")
    public String showCharacterCountPage(Model model, Authentication authentication) {
        // 인증 여부 확인
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        if (!isAuthenticated) {
            return "redirect:/login";
        }

        return "character_count"; // character_count.html 반환
    }

    @GetMapping("/salary_calculator")
    public String showSalaryCalculatorPage(Model model, Authentication authentication) {
        // 인증 여부 확인
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        if (!isAuthenticated) {
            return "redirect:/login";
        }

        return "salary_calculator"; // character_count.html 반환
    }

    @GetMapping("/retirement_calculator")
    public String showRetirementCalculatorPage(Model model, Authentication authentication) {
        // 인증 여부 확인
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        if (!isAuthenticated) {
            return "redirect:/login";
        }

        return "retirement_calculator"; // character_count.html 반환
    }

    @GetMapping("/unemployment_calculator")
    public String showUnemploymentCalculatorPage(Model model, Authentication authentication) {
        // 인증 여부 확인
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        if (!isAuthenticated) {
            return "redirect:/login";
        }

        return "unemployment_calculator"; // character_count.html 반환
    }

    @GetMapping("/graduation_calculator")
    public String showGraduationCalculatorPage(Model model, Authentication authentication) {
        // 인증 여부 확인
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        if (!isAuthenticated) {
            return "redirect:/login";
        }

        return "graduation_calculator"; // character_count.html 반환
    }
}
