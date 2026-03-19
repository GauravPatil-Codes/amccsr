package com.ahmedabad.csr.services;

import java.util.Map;

public interface EmailService {
  void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String htmlBody);

    /**
     * Send welcome email to newly registered users
     * @param to recipient email address
     * @param name recipient name
     */
    void sendWelcomeEmail(String to, String name);

    /**
     * Send thank you email when user shows interest in a project
     * @param to recipient email address
     * @param name recipient name
     * @param projectName name of the project
     * @param projectDetails map containing project details (category, location, budget, impact)
     */
    void sendProjectInterestEmail(String to, String name, String projectName, Map<String, Object> projectDetails);
}
