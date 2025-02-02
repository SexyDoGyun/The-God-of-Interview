package capstone.interview.controller;

import capstone.interview.service.NaverMailPasswordService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailAuthController {
    private final NaverMailPasswordService mailService;

    @PostMapping("/send-verification-code")
    public void sendVerificationCode(@RequestBody Map<String, String> request, HttpSession session) throws Exception {
        String email = request.get("email");
        String code = mailService.sendSimpleMessage(email); // 메서드 호출 후 반환된 인증 코드 저장
        session.setAttribute("emailVerificationCode", code);
    }

    @PostMapping("/verify-code")
    public boolean verifyCode(@RequestBody Map<String, String> request, HttpSession session) {
        String email = request.get("email");
        String code = request.get("code");
        String sessionCode = (String) session.getAttribute("emailVerificationCode");
        return code.equals(sessionCode);
    }
}



