package capstone.interview.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NaverMailPasswordService {
    private final JavaMailSender emailSender;

    public String sendSimpleMessage(String to) throws MessagingException, UnsupportedEncodingException {
        String verificationCode = generateVerificationCode();
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("인증 코드 발송");

        String msg = "<div style='margin:100px;'>";
        msg += "<h1> 안녕하세요 면접의 神입니다.</h1>";
        msg += "<p>아래 코드는 당신의 인증 코드입니다. 회원가입 페이지에서 인증 코드를 입력해주세요.<p>";
        msg += "<br>";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msg += "<div style='font-size:130%;'>";
        msg += "CODE : <strong>" + verificationCode + "</strong><div><br/> ";
        msg += "</div>";
        msg += "<br>";
        msg += "</div>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress("seok6607@naver.com", "면접의 神"));

        emailSender.send(message);

        return verificationCode;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 인증 코드 생성
        return String.valueOf(code);
    }
}

