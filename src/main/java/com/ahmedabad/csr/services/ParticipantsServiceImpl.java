package com.ahmedabad.csr.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Participants;
import com.ahmedabad.csr.repository.ParticipantsRepository;

import java.util.UUID;

@Service
public class ParticipantsServiceImpl implements ParticipantsService {

    @Autowired
    private ParticipantsRepository participantsRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(ParticipantsService.class);

    // @Override
    // public Participants saveParticipant(Participants participant) {
    // return participantsRepository.save(participant);
    // }

    @Override
    public List<Participants> getAllParticipants() {
        return participantsRepository.findAll();
    }

    @Override
    public Optional<Participants> getParticipantById(int id) {
        return participantsRepository.findById(id);
    }

    @Override
    public Participants updateParticipant(int id, Participants participant) {
        return participantsRepository.findById(id).map(existing -> {
            existing.setParticipantName(participant.getParticipantName());
            existing.setOrganizationName(participant.getOrganizationName());
            existing.setParticipantEmail(participant.getParticipantEmail());
            existing.setParticipantMobileNumber(participant.getParticipantMobileNumber());
            existing.setAmount(participant.getAmount());
            existing.setStatus(participant.getStatus());
            existing.setNote(participant.getNote());
            return participantsRepository.save(existing);
        }).orElse(null);
    }

    @Override
    public void deleteParticipant(int id) {
        participantsRepository.deleteById(id);
    }

    // @Override
    // public Participants saveParticipant(Participants participant) {
    // String token = generateUniqueToken();
    // participant.setToken(token);
    // return participantsRepository.save(participant);
    // }

    @Override
    public Participants saveParticipant(Participants participant) {
        // Generate unique token
        String token = generateUniqueToken();
        participant.setToken(token);

        // Save participant to database
        Participants savedParticipant = participantsRepository.save(participant);

        // Send email with token
        sendTokenEmail(savedParticipant);

        return savedParticipant;
    }

    private void sendTokenEmail(Participants participant) {
        try {
            String subject = "Your Participant Application Token - Please Keep Safe";
            String htmlBody = createEmailBody(participant);

            emailService.sendHtmlEmail(participant.getParticipantEmail(), subject, htmlBody);
            logger.info("Token email sent successfully to: {}", participant.getParticipantEmail());

        } catch (Exception e) {
            logger.error("Failed to send token email to: {}", participant.getParticipantEmail(), e);
        }
    }

    private String createEmailBody(Participants participant) {
        String name = participant.getParticipantEmail() != null ? participant.getParticipantEmail() : "Participant";
        String token = participant.getToken() != null ? participant.getToken() : "N/A";

        return "<html><head><meta charset='UTF-8'>\r\n" +
                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\r\n" +
                "  <style>\r\n" +
                "    .amd-logo, .amd-name, .GovLogo {\r\n" +
                "      height: 50px;\r\n" +
                "      width: 50px;\r\n" +
                "      margin: 0 5px;\r\n" +
                "    }\r\n" +
                ".amd-name{\r\n+ justify-content: space-around;}" +
                "    .header-wrapper {\r\n" +
                "      width: 100%;\r\n" +
                "      display: flex;\r\n" +
                "      justify-content: center;\r\n" +
                "      align-items: center;\r\n" +
                "      padding: 20px 0;\r\n" +
                "    }\r\n" +
                "    .navbar-brand {\r\n" +
                "      display: flex;\r\n" +
                "      align-items: center;\r\n" +
                "    }\r\n" +
                "  </style>\r\n" +
                "</head><body style='font-family: Arial, sans-serif;'>\r\n" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>\r\n" +
                "<div class='header-wrapper'>\r\n" +
                "  <a class='navbar-brand' href='index.html'>\r\n" +
                "    <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amcLogo.png' class='amd-logo' style='width:50px' alt='Logo'>\r\n"
                +
                "    <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amc%20logo.png' class='amd-name' style='width:200px' alt='Logo'>\r\n"
                +
                "    <img src='https://gcsra.org/writereaddata/Images/gov-gujarat-logo.png' class='GovLogo' alt='GovLogo'>\r\n"
                +
                "  </a>\r\n" +
                "</div>\r\n" +

                "<h2 style='color: #2c3e50;'>Participant Application Confirmation</h2>\r\n" +
                "<p>Dear <strong>" + name + "</strong>,</p>\r\n" +
                "<p>Thank you for your participation application!</p>\r\n" +
                "<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>\r\n" +
                "<p><strong>Your Participant Token:</strong></p>\r\n" +
                "<p style='font-size: 18px; font-weight: bold; color: #007bff;'>" + token + "</p>\r\n" +
                "</div>\r\n" +
                "<p>Please use this token for tracking your application status and future communications.</p>\r\n" +
                "<p style='color: #e74c3c;'><strong>Important:</strong> Please keep this token safe and do not share it with others.</p>\r\n"
                +
                "<p>Best regards,<br>The Application Team</p>\r\n" +
                "</div></body></html>";

    }

    private String generateUniqueToken() {
        String token;
        do {
            token = "PA" + String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
        } while (participantsRepository.existsByToken(token));
        return token;
    }

}
