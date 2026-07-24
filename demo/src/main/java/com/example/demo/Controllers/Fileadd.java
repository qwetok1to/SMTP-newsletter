package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Servise.Addtemplate;

@RestController
public class Fileadd { 

   @Autowired
   Addtemplate addtemplate;

  @PostMapping("/Sendtemplate")
    public ResponseEntity<String> Sendtemplate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {

        String templateId = addtemplate.saveTemplate(file, description);
        return ResponseEntity.ok(templateId);
    }
}
