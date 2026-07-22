package com.example.demo.Controllers;

import com.example.demo.DTO.Email;
import com.example.demo.Servise.EmailServise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    EmailServise emailService;

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody Email dto) {
        if (dto.mail == null || dto.mail.isEmpty()) {
            return ResponseEntity.badRequest().body("Collection mail must not be empty");
        }

        for (String email : dto.mail) {
            if (email == null || email.isBlank()) {
                continue;
            }

            emailService.sendEmail(
                    email,
                    "Test message",
                    "This is a test email"
            );
        }

        return ResponseEntity.ok("Emails sent");
    }
}
