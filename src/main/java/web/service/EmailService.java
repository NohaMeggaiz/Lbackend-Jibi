package web.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String recipient, String subject, String content)
            throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setFrom("appjibi.contact@gmail.com");
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(msg);
        System.out.println("Mail Sent Successfully");
    }
}
