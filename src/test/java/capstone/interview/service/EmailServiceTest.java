package capstone.interview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest
public class EmailServiceTest {

    private static final Logger LOGGER = Logger.getLogger(EmailServiceTest.class.getName());

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testSendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kimdk0903@naver.com");  // 테스트할 이메일 주소
        message.setSubject("Test Email");
        message.setText("This is a test email from Spring Boot application.");

        try {
            mailSender.send(message);
            LOGGER.log(Level.INFO, "Test email sent successfully.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to send test email.", ex);
        }
    }
}
