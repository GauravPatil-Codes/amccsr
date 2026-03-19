package com.ahmedabad.csr.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
    
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indicates HTML

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }

    @Override
    public void sendWelcomeEmail(String to, String name) {
        try {
            logger.info("Attempting to send welcome email to: {}", to);
            String htmlTemplate = loadHtmlTemplate("welcome-email.html");
            String personalizedHtml = htmlTemplate.replace("{{name}}", name != null ? name : "User");

            sendHtmlEmail(to, "Welcome to AMC CSR Portal - Registration Successful!", personalizedHtml);
            logger.info("Welcome email sent successfully to: {}", to);
        } catch (IOException e) {
            logger.error("Failed to load welcome email template: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load email template", e);
        } catch (Exception e) {
            logger.error("Failed to send welcome email to {}: {} - Type: {}", to, e.getMessage(), e.getClass().getName());
            if (e.getCause() != null) {
                logger.error("Root cause: {} - {}", e.getCause().getClass().getName(), e.getCause().getMessage());
            }
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }

    @Override
    public void sendProjectInterestEmail(String to, String name, String projectName, Map<String, Object> projectDetails) {
        try {
            logger.info("Attempting to send project interest email to: {} for project: {}", to, projectName);
            String htmlTemplate = loadHtmlTemplate("project-interest-email.html");
            String personalizedHtml = htmlTemplate
                    .replace("{{name}}", name != null ? name : "User")
                    .replace("{{projectName}}", projectName != null ? projectName : "this project")
                    .replace("{{category}}", projectDetails != null ? String.valueOf(projectDetails.getOrDefault("category", "N/A")) : "N/A")
                    .replace("{{location}}", projectDetails != null ? String.valueOf(projectDetails.getOrDefault("location", "N/A")) : "N/A")
                    .replace("{{budget}}", projectDetails != null ? String.valueOf(projectDetails.getOrDefault("budget", "N/A")) : "N/A")
                    .replace("{{impact}}", projectDetails != null ? String.valueOf(projectDetails.getOrDefault("impact", "N/A")) : "N/A");

            sendHtmlEmail(to, "Thank You for Your Interest in " + projectName, personalizedHtml);
            logger.info("Project interest email sent successfully to: {} for project: {}", to, projectName);
        } catch (IOException e) {
            logger.error("Failed to load project interest email template: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load email template", e);
        } catch (Exception e) {
            logger.error("Failed to send project interest email to {}: {} - Type: {}", to, e.getMessage(), e.getClass().getName());
            if (e.getCause() != null) {
                logger.error("Root cause: {} - {}", e.getCause().getClass().getName(), e.getCause().getMessage());
            }
            throw new RuntimeException("Failed to send project interest email", e);
        }
    }

    /**
     * Load HTML template from resources/templates directory
     */
    private String loadHtmlTemplate(String templateName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/" + templateName)) {
            if (inputStream == null) {
                throw new IOException("Template not found: templates/" + templateName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
