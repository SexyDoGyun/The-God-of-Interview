package capstone.interview.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class NaverMailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendAuthCode(String toEmail) {
        String authCode = generateAuthCode();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("seok6607@naver.com"); // 발신자 이메일 설정
            helper.setTo(toEmail);
            helper.setSubject("면접의 神 인증번호");
            helper.setText("인증번호: " + authCode);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("이메일 전송 중 문제가 발생했습니다.");
        }

        return authCode;
    }

    private String generateAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}

