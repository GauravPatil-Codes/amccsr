package com.ahmedabad.csr.services;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        String name = participant.getParticipantName() != null ? participant.getParticipantName()
                : (participant.getParticipantEmail() != null ? participant.getParticipantEmail() : "Participant");
        String token = participant.getToken() != null ? participant.getToken() : "N/A";
        String currentDate = new SimpleDateFormat("dd MMMM yyyy").format(new Date());

        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "  <meta charset='UTF-8'>" +
                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "  <title>Participant Application Confirmation</title>" +
                "  <style>" +
                "    * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; line-height: 1.6; }"
                +
                "    .email-container { max-width: 650px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); width: 95%; }"
                +
                "    .header { background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); padding: 30px 20px; text-align: center; }"
                +
                "    .logo-section { display: flex; justify-content: center; align-items: center; gap: 15px; margin-bottom: 20px; padding:20px; background-color:#ffffff;border-radius:15px; }"
                +
                "    .logo { height: 60px; transition: transform 0.3s ease; }" +
                "    .logo:hover { transform: scale(1.05); }" +
                "    .header-title { color: #ffffff; font-size: 28px; font-weight: 600; margin: 0; text-shadow: 0 2px 4px rgba(0,0,0,0.3); }"
                +
                "    .header-subtitle { color: #e8f4fd; font-size: 16px; margin-top: 8px; font-weight: 300; }" +
                "    .content { padding: 40px 30px; }" +
                "    .greeting { font-size: 20px; color: #2c3e50; margin-bottom: 20px; font-weight: 500; }" +
                "    .message { color: #555555; font-size: 16px; margin-bottom: 25px; }" +
                "    .token-container { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 12px; padding: 25px; margin: 30px 0; text-align: center; position: relative; overflow: hidden; }"
                +
                "    .token-container::before { content: ''; position: absolute; top: -50%; left: -50%; width: 200%; height: 200%; background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%); animation: shimmer 3s ease-in-out infinite; }"
                +
                "    .token-label { color: #ffffff; font-size: 16px; font-weight: 600; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 1px; }"
                +
                "    .token-value { color: #ffffff; font-size: 32px; font-weight: 700; font-family: 'Courier New', monospace; letter-spacing: 3px; text-shadow: 0 2px 4px rgba(0,0,0,0.3); position: relative; z-index: 1; }"
                +
                "    .copy-hint { color: #e8f4fd; font-size: 12px; margin-top: 10px; font-style: italic; }" +
                "    .instructions { background: #f8f9fa; border-left: 4px solid #28a745; padding: 20px; margin: 25px 0; border-radius: 0 8px 8px 0; }"
                +
                "    .instructions h3 { color: #28a745; font-size: 18px; margin-bottom: 10px; }" +
                "    .instructions ul { color: #555555; padding-left: 20px; }" +
                "    .instructions li { margin-bottom: 8px; }" +
                "    .warning { background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 8px; padding: 15px; margin: 20px 0; display: flex; align-items: center; }"
                +
                "    .warning-icon { color: #f39c12; font-size: 20px; margin-right: 10px; }" +
                "    .warning-text { color: #856404; font-weight: 500; }" +
                "    .footer { background: #2c3e50; color: #ffffff; padding: 30px; text-align: center; }" +
                "    .footer-content { margin-bottom: 20px; }" +
                "    .contact-info { background: rgba(255,255,255,0.1); border-radius: 8px; padding: 15px; margin-top: 20px; }"
                +
                "    .contact-info h4 { margin-bottom: 10px; color: #ecf0f1; }" +
                "    .contact-details { font-size: 14px; line-height: 1.8; }" +
                "    .divider { height: 1px; background: linear-gradient(to right, transparent, #bdc3c7, transparent); margin: 25px 0; }"
                +
                "    .application-id { background: #ecf0f1; padding: 12px; border-radius: 6px; margin: 15px 0; text-align: center; }"
                +
                "    .application-id strong { color: #2c3e50; }" +
                "    @keyframes shimmer { 0%, 100% { transform: translateX(-100%); } 50% { transform: translateX(100%); } }"
                +
                "    @media (max-width: 768px) {" +
                "      .email-container { margin: 5px !important; border-radius: 8px !important; max-width: 100% !important; }"
                +
                "      .content { padding: 20px 15px !important; }" +
                "      .header { padding: 20px 15px !important; }" +
                "      .logo-section { flex-direction: row !important; flex-wrap: wrap !important; gap: 8px !important; padding: 15px !important; }"
                +
                "      .logo { height: 45px !important; max-width: 80px !important; }" +
                "      .header-title { font-size: 22px !important; }" +
                "      .header-subtitle { font-size: 14px !important; }" +
                "      .greeting { font-size: 18px !important; }" +
                "      .message { font-size: 14px !important; }" +
                "      .token-container { padding: 20px 15px !important; margin: 20px 0 !important; }" +
                "      .token-value { font-size: 20px !important; letter-spacing: 2px !important; word-break: break-all !important; }"
                +
                "      .token-label { font-size: 14px !important; }" +
                "      .instructions { padding: 15px !important; margin: 20px 0 !important; }" +
                "      .instructions h3 { font-size: 16px !important; }" +
                "      .instructions ul { padding-left: 15px !important; }" +
                "      .instructions li { font-size: 14px !important; }" +
                "      .warning { padding: 12px !important; flex-direction: column !important; text-align: center !important; }"
                +
                "      .warning-icon { margin-right: 0 !important; margin-bottom: 5px !important; }" +
                "      .warning-text { font-size: 14px !important; }" +
                "      .footer { padding: 20px 15px !important; }" +
                "      .contact-info { padding: 12px !important; }" +
                "      .contact-details { font-size: 12px !important; }" +
                "      .application-id { padding: 10px !important; }" +
                "    }" +
                "    @media (max-width: 480px) {" +
                "      .logo-section { flex-direction: column !important; align-items: center !important; }" +
                "      .logo { height: 40px !important; }" +
                "      .header-title { font-size: 20px !important; }" +
                "      .token-value { font-size: 18px !important; }" +
                "      .content { padding: 15px 10px !important; }" +
                "      .footer { padding: 15px 10px !important; }" +
                "    }" +
                "    .logo-section img:nth-child(2) { max-width: 150px !important; height: auto !important; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='email-container'>" +
                "    <div class='header'>" +
                "      <div class='logo-section'>" +
                "        <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amcLogo.png' class='logo' alt='AMC Logo'>"
                +
                "        <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amc%20logo.png' class='logo' style='height: 45px;width: 200px;margin-top: 10px;margin-left: 10px;' alt='AMC Name'>"
                +
                "        <img src='https://gcsra.org/writereaddata/Images/gov-gujarat-logo.png' class='logo' alt='Gujarat Government Logo'>"
                +
                "      </div>" +
                "      <h1 class='header-title'>Application Confirmed</h1>" +
                "      <p class='header-subtitle'>Corporate Social Responsibility Initiative</p>" +
                "    </div>" +
                "" +
                "    <div class='content'>" +
                "      <div class='greeting'>Dear " + name + ",</div>" +
                "" +
                "      <div class='message'>" +
                "        Thank you for your valuable participation in our Corporate Social Responsibility (CSR) initiative. "
                +
                "        We are pleased to confirm that your application has been successfully received and processed."
                +
                "      </div>" +
                "" +
                "      <div class='application-id'>" +
                "        <strong>Application Date:</strong> " + currentDate +
                "      </div>" +
                "" +
                "      <div class='token-container'>" +
                "        <div class='token-label'>Your Unique Reference Token</div>" +
                "        <div class='token-value'>" + token + "</div>" +
                "        <div class='copy-hint'>Please save this token for future reference</div>" +
                "      </div>" +
                "" +
                "      <div class='instructions'>" +
                "        <h3>📋 Important Instructions:</h3>" +
                "        <ul>" +
                "          <li><strong>Save this token:</strong> Use it to track your application status and for all future communications</li>"
                +
                "          <li><strong>Keep it secure:</strong> Do not share this token with unauthorized persons</li>"
                +
                "          <li><strong>Reference number:</strong> Quote this token in all correspondence with our office</li>"
                +
                "          <li><strong>Status updates:</strong> You will receive notifications about your application progress</li>"
                +
                "        </ul>" +
                "      </div>" +
                "" +
                "      <div class='warning'>" +
                "        <span class='warning-icon'>⚠️</span>" +
                "        <span class='warning-text'>" +
                "          <strong>Security Notice:</strong> This token is confidential and should only be used by the applicant. "
                +
                "          If you suspect any unauthorized use, please contact us immediately." +
                "        </span>" +
                "      </div>" +
                "" +
                "      <div class='divider'></div>" +
                "" +
                "      <div class='message'>" +
                "        Our team will review your application and update you on the progress. " +
                "        If you have any questions or need assistance, please don't hesitate to contact us using the information provided below."
                +
                "      </div>" +
                "    </div>" +
                "" +
                "    <div class='footer'>" +
                "      <div class='footer-content'>" +
                "        <strong>Best regards,</strong><br>" +
                "        <strong>Ahmedabad Municipal Corporation</strong><br>" +
                "        Corporate Social Responsibility Department" +
                "      </div>" +
                "" +
                "      <div class='contact-info'>" +
                "        <h4>📞 Contact Information</h4>" +
                "        <div class='contact-details'>" +
                "          <strong>Email:</strong> ccrs@ahmedabadcity.gov.in<br>" +
                "          <strong>Office:</strong> Ahmedabad Municipal Corporation<br>" +
                "          <strong>Address:</strong> Dr. Karmavir Bhaurao Patil Bhavan, Usmanpura, Ahmedabad - 380013<br>"
                +
                "          <strong>Working Hours:</strong> Monday to Friday, 10:30 AM to 6:00 PM" +
                "        </div>" +
                "      </div>" +
                "" +
                "      <div style='margin-top: 20px; font-size: 12px; color: #bdc3c7;'>" +
                "        This is an automated email. Please do not reply directly to this message.<br>" +
                "        © " + java.time.Year.now() + " Ahmedabad Municipal Corporation. All rights reserved." +
                "      </div>" +
                "    </div>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }

    private String generateUniqueToken() {
        String token;
        do {
            token = "PA" + String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
        } while (participantsRepository.existsByToken(token));
        return token;
    }

}
