package com.example.demo.Servise;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServise {

    private final JavaMailSender mailSender;
    private final Addtemplate addtemplate;

    public EmailServise(JavaMailSender mailSender, Addtemplate addtemplate) {
        this.mailSender = mailSender;
        this.addtemplate = addtemplate;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("kitigo71@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendEmail(String to, String subject, String text, String idTemplate) {
        if (idTemplate == null || idTemplate.isBlank()) {
            sendEmail(to, subject, text);
            return;
        }

        String html = addtemplate.loadTemplate2(idTemplate)
                .replace("{{name}}", subject == null ? "" : subject)
                .replace("{{text}}", text == null ? "" : text);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom("kitigo71@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send template email: " + e.getMessage(), e);
        }
    }
}
