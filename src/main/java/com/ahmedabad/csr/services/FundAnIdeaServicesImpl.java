package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.FundAnIdea;
import com.ahmedabad.csr.repository.FundAnIdeaRepository;

@Service
public class FundAnIdeaServicesImpl implements FundAnIdeaServices {

    @Autowired
    private FundAnIdeaRepository fundAnIdeaRepository;

      @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(ParticipantsService.class);

    // @Override
    // public FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea) {
    // return fundAnIdeaRepository.save(fundAnIdea);
    // }

    @Override
    public List<FundAnIdea> getAllFundAnIdea() {
        return fundAnIdeaRepository.findAll();
    }

    @Override
    public Optional<FundAnIdea> getFundAnIdeaById(int fundanideaid) {
        return fundAnIdeaRepository.findById(fundanideaid);
    }

    @Override
    public FundAnIdea updateFundAnIdea(int fundanideaid, FundAnIdea fundAnIdea) {
        return fundAnIdeaRepository.findById(fundanideaid).map(existing -> {
            existing.setNatureofproject(fundAnIdea.getNatureofproject());
            existing.setFundanideaprojectname(fundAnIdea.getFundanideaprojectname());
            existing.setFundanideaprojectlocation(fundAnIdea.getFundanideaprojectlocation());
            existing.setFundanideadepartment(fundAnIdea.getFundanideadepartment());
            existing.setFundanideadocement(fundAnIdea.getFundanideadocement());
            existing.setFundanideadescription(fundAnIdea.getFundanideadescription());
            existing.setFundanideaorganizationname(fundAnIdea.getFundanideaorganizationname());
            existing.setFundanideaemailid(fundAnIdea.getFundanideaemailid());
            existing.setFundanideaphonenumber(fundAnIdea.getFundanideaphonenumber());
            existing.setFundanideacontactpersonname(fundAnIdea.getFundanideacontactpersonname());
            existing.setFundanideaestimateamount(fundAnIdea.getFundanideaestimateamount());
            existing.setFundanideastatus(fundAnIdea.getFundanideastatus());

            return fundAnIdeaRepository.save(existing);
        }).orElse(null);
    }

    @Override
    public void deleteFundAnIdea(int fundanideaid) {
        fundAnIdeaRepository.deleteById(fundanideaid);
    }

    // @Override
    // public FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea) {
    //     String token = generateUniqueToken();
    //     fundAnIdea.setFundanideatoken(token);
    //     return fundAnIdeaRepository.save(fundAnIdea);
    // }

    // private String generateUniqueToken() {
    //     String token;
    //     do {
    //         // Generate 10 random digits
    //         String randomPart = String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
    //         token = "FA" + randomPart;
    //     } while (fundAnIdeaRepository.existsByFundanideatoken(token));
    //     return token;
    // }

    @Override
public FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea) {
    // Generate unique token
    String token = generateUniqueToken();
    fundAnIdea.setFundanideatoken(token);
    
    // Save fund an idea to database
    FundAnIdea savedFundAnIdea = fundAnIdeaRepository.save(fundAnIdea);
    
    // Send email with token
    sendTokenEmail(savedFundAnIdea);
    
    return savedFundAnIdea;
}

private void sendTokenEmail(FundAnIdea fundAnIdea) {
    try {
        String subject = "Your Fund an Idea Application Token - Please Keep Safe";
        String htmlBody = createEmailBody(fundAnIdea);

        emailService.sendHtmlEmail(fundAnIdea.getFundanideaemailid(), subject, htmlBody);
        logger.info("Token email sent successfully to: {}", fundAnIdea.getFundanideaemailid());

    } catch (Exception e) {
        logger.error("Failed to send token email to: {}", fundAnIdea.getFundanideaemailid(), e);
    }
}

private String createEmailBody(FundAnIdea fundAnIdea) {
    String name = fundAnIdea.getFundanideaemailid() != null ? fundAnIdea.getFundanideaemailid() : "Applicant";
    String token = fundAnIdea.getFundanideatoken() != null ? fundAnIdea.getFundanideatoken() : "N/A";

    return "<html><head><meta charset='UTF-8'>\r\n" +
            "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\r\n" +
            "  <style>\r\n" +
            "    .amd-logo, .amd-name, .GovLogo {\r\n" +
            "      height: 50px;\r\n" +
            "      width: 50px;\r\n" +
            "      margin: 0 5px;\r\n" +
            "    }\r\n" +
            ".amd-name{\r\n" +
            "justify-content: space-around;}" +
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

            "<h2 style='color: #2c3e50;'>Fund an Idea Application Confirmation</h2>\r\n" +
            "<p>Dear <strong>" + name + "</strong>,</p>\r\n" +
            "<p>Thank you for your Fund an Idea application!</p>\r\n" +
            "<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>\r\n" +
            "<p><strong>Your Fund an Idea Token:</strong></p>\r\n" +
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
        // Generate 10 random digits
        String randomPart = String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
        token = "FA" + randomPart;
    } while (fundAnIdeaRepository.existsByFundanideatoken(token));
    return token;
}
}
