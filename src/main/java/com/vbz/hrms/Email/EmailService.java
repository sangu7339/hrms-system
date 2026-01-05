package com.vbz.hrms.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPassword(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("seegisangmesh@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
        javaMailSender.send(message);
//           javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}

