package com.example.demo.Servise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class Addtemplate {

    private static final Path STORAGE_DIR = Paths.get("uploaded-templates");

    public Addtemplate() {
        try {
            Files.createDirectories(STORAGE_DIR);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create template storage directory", e);
        }
    }

    public String saveTemplate(MultipartFile file, String description) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Template file is required");
        }

        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String lowerName = originalName == null ? "" : originalName.toLowerCase();
        if (!lowerName.endsWith(".html") && !lowerName.endsWith(".htm")) {
            throw new IllegalArgumentException("Only HTML files are accepted");
        }

        String templateId = UUID.randomUUID().toString();
        Path targetFile = STORAGE_DIR.resolve(templateId + ".html");

        try {
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            if (description != null && !description.isBlank()) {
                Path metadataFile = STORAGE_DIR.resolve(templateId + ".txt");
                Files.writeString(metadataFile, description);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save template file", e);
        }

        return templateId;
    }
}