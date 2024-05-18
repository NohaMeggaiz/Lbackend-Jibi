package web.service;


import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    /*@Autowired
    private JavaMailSender javaMailSender;


    public void sendEmail(String recipient, String content) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setFrom("appjibi.contact@gmail.com");
        helper.setTo(recipient);
        helper.setSubject("JIBI Application Services");
        helper.setText(content, true);

        javaMailSender.send(msg);
        System.out.println("Mail Sent Successfully");
    }*/
}


