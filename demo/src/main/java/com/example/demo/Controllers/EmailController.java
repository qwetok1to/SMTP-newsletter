package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.EmailInput;
import com.example.demo.Servise.EmailServise;

@Controller
public class EmailController {
    @Autowired  
    EmailServise emailService;


    

    @MutationMapping
    public String sendEmails(@Argument EmailInput input) {

        for (String email : input.getMail()) {

            if (email == null || email.isBlank()) {
                continue;
            }

            emailService.sendEmail(
                    email,
                    input.getName(),
                    input.getText()
            );
        }

        return "Emails sent";
    }
}
