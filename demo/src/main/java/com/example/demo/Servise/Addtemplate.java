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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class Addtemplate {

    private static final Logger logger = LoggerFactory.getLogger(Addtemplate.class);
    private static final Path STORAGE_DIR = Paths.get("uploaded-templates");

    public Addtemplate() {
        try {
            Files.createDirectories(STORAGE_DIR);
            logger.info("Template storage directory created at: " + STORAGE_DIR.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Cannot create template storage directory at: " + STORAGE_DIR, e);
            throw new IllegalStateException("Cannot create template storage directory: " + e.getMessage(), e);
        }
    }

    public String saveTemplate(MultipartFile file) {
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
        } catch (IOException e) {
            logger.error("Failed to save template file to: " + targetFile, e);
            throw new IllegalStateException("Failed to save template file: " + e.getMessage(), e);
        }

        return templateId;
    }

    public String loadTemplate2(String templateId) {
        if (templateId == null || templateId.isBlank()) {
            throw new IllegalArgumentException("Template id is required");
        }

        Path templateFile = STORAGE_DIR.resolve(templateId + ".html");
        if (!templateFile.normalize().startsWith(STORAGE_DIR.normalize()) || !Files.exists(templateFile)) {
            throw new IllegalArgumentException("Template not found: " + templateId);
        }

        try {
            return Files.readString(templateFile);
        } catch (IOException e) {
            logger.error("Failed to read template file: " + templateFile, e);
            throw new IllegalStateException("Failed to read template file: " + e.getMessage(), e);
        }
    }
}