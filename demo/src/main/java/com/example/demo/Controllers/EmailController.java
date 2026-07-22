package com.example.demo.Controllers;

import com.example.demo.DTO.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class EmailController {

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody Email dto) {
        for(String email : dto.mail) {
            System.out.println(email);
        }
    
        return ResponseEntity.ok("Request received");
    }
}
