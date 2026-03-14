package com.campuskart.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationMail(String toEmail, String name) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Welcome to CampusKart 🎉");

            String htmlContent =
                    "<div style='font-family: Arial; text-align:center;'>"
                            + "<img src='cid:campusLogo' width='120'/><br><br>"
                            + "<h2>Welcome to CampusKart 🚀</h2>"
                            + "<p>Hi <b>" + name + "</b>,</p>"
                            + "<p>Your account has been created successfully.</p>"
                            + "<p>Start buying and selling within your campus now!</p>"
                            + "<br>"
                            + "<button style='background:#4CAF50;color:white;padding:10px 20px;border:none;border-radius:5px;'>Login Now</button>"
                            + "<br><br>"
                            + "<small>Team CampusKart</small>"
                            + "</div>";

            helper.setText(htmlContent, true);

            // attach image
            ClassPathResource image =
                    new ClassPathResource("static/Campuskart.png");

            helper.addInline("campusLogo", image);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
