package capstone.interview.controller;

import capstone.interview.model.User;
import capstone.interview.service.NaverMailService;
import capstone.interview.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private NaverMailService mailService;

    private String generatedAuthCode; // 인증번호 저장
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/delete-account")
    public String showDeleteAccountPage() {
        return "delete-account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(@RequestParam("password") String password, Model model) {
        boolean isDeleted = userService.deleteAccount(password);

        if (isDeleted) {
            return "redirect:/logout";
        } else {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "delete-account";
        }
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
            return "change-password";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty() || !passwordEncoder.matches(currentPassword, userOptional.get().getPassword())) {
            model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
            return "change-password";
        }

        User user = userOptional.get();
        user.setPassword(newPassword);  // 비밀번호는 인코딩되지 않은 상태로 설정합니다.
        userService.save(user);  // save 메서드에서 비밀번호를 인코딩하고 저장합니다.

        return "redirect:/profile";
    }
    // 아이디 찾기 페이지로 이동하는 GET 요청 처리
    @GetMapping("/find-username")
    public String showFindUsernamePage() {
        return "find-username"; // find-username.html 파일을 반환
    }
    @PostMapping("/find-username/send-code")
    public String sendAuthCode(@RequestParam("email") String email, HttpSession session, Model model) {
        String authCode = mailService.sendAuthCode(email); // 인증번호 생성 및 전송

        session.setAttribute("authCode", authCode); // 인증번호를 세션에 저장
        session.setAttribute("email", email); // 이메일을 세션에 저장

        model.addAttribute("authCodeSent", true); // 인증번호 입력란 표시 플래그
        model.addAttribute("email", email); // 입력된 이메일 유지

        return "find-username"; // 동일 페이지 반환
    }


    // 인증번호 검증 및 아이디 반환
    @PostMapping("/find-username/verify-code")
    public String verifyAuthCode(@RequestParam("authCode") String inputCode, HttpSession session, Model model) {
        String savedAuthCode = (String) session.getAttribute("authCode"); // 세션에서 저장된 인증번호 가져오기
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 가져오기

        if (savedAuthCode != null && savedAuthCode.equals(inputCode)) {
            // 인증번호가 일치하면 아이디 찾기
            String username = userService.findUsernameByEmail(email);

            model.addAttribute("username", username);
            return "found-username"; // 성공 페이지로 이동
        } else {
            // 인증번호가 유효하지 않은 경우
            model.addAttribute("error", "인증번호가 유효하지 않습니다.");
            model.addAttribute("authCodeSent", true); // 인증번호 입력란 유지
            return "find-username"; // 인증번호 입력 페이지로 돌아감
        }
    }
    // 비밀번호 찾기 페이지로 이동
    @GetMapping("/find-password")
    public String showFindPasswordPage() {
        return "find-password"; // find-password.html 반환
    }

    // 비밀번호 찾기: 이메일로 인증번호 전송
    @PostMapping("/find-password/send-code")
    public String sendPasswordAuthCode(@RequestParam("email") String email,
                                       @RequestParam("username") String username,
                                       HttpSession session,
                                       Model model) {
        User user = userService.findUserByEmail(email);
        if (user == null || !user.getUsername().equals(username)) {
            model.addAttribute("error", "입력한 이메일 또는 아이디가 올바르지 않습니다.");
            return "find-password"; // 다시 비밀번호 찾기 페이지
        }

        String authCode = mailService.sendAuthCode(email); // 인증번호 생성 및 전송
        session.setAttribute("authCode", authCode); // 인증번호 세션 저장
        session.setAttribute("email", email); // 이메일 세션 저장

        model.addAttribute("authCodeSent", true); // 인증번호 입력란 표시 플래그
        model.addAttribute("email", email); // 입력된 이메일 유지
        return "find-password";
    }

    // 비밀번호 재설정 페이지로 이동
    @PostMapping("/find-password/verify-code")
    public String verifyPasswordAuthCode(@RequestParam("authCode") String inputCode,
                                         HttpSession session,
                                         Model model) {
        String savedAuthCode = (String) session.getAttribute("authCode");
        String email = (String) session.getAttribute("email");

        if (savedAuthCode != null && savedAuthCode.equals(inputCode)) {
            model.addAttribute("email", email); // 비밀번호 재설정 페이지로 이메일 전달
            return "reset-password"; // 비밀번호 재설정 페이지 반환
        } else {
            model.addAttribute("error", "인증번호가 유효하지 않습니다.");
            model.addAttribute("authCodeSent", true);
            return "find-password"; // 다시 인증번호 입력 페이지
        }
    }

    // 비밀번호 재설정 처리
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        System.out.println("Reset Password Request - Email: " + email);

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
            return "reset-password";
        }

        try {
            userService.resetPassword(email, newPassword);
            System.out.println("Password reset successful for email: " + email);
        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 재설정에 실패했습니다: " + e.getMessage());
            return "reset-password";
        }

        return "redirect:/login";
    }



}