package com.ahmedabad.csr.services;

public interface EmailService {
  void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String htmlBody);
}
