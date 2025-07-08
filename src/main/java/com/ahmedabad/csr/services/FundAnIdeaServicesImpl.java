package com.ahmedabad.csr.services;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    // String token = generateUniqueToken();
    // fundAnIdea.setFundanideatoken(token);
    // return fundAnIdeaRepository.save(fundAnIdea);
    // }

    // private String generateUniqueToken() {
    // String token;
    // do {
    // // Generate 10 random digits
    // String randomPart = String.format("%010d", (long) (Math.random() *
    // 1_000_000_0000L));
    // token = "FA" + randomPart;
    // } while (fundAnIdeaRepository.existsByFundanideatoken(token));
    // return token;
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
    String name = fundAnIdea.getFundanideacontactpersonname() != null ? fundAnIdea.getFundanideacontactpersonname()
            : (fundAnIdea.getFundanideaemailid() != null ? fundAnIdea.getFundanideaemailid() : "Applicant");
    String token = fundAnIdea.getFundanideatoken() != null ? fundAnIdea.getFundanideatoken() : "N/A";
    String organizationName = fundAnIdea.getFundanideaorganizationname() != null
            ? fundAnIdea.getFundanideaorganizationname()
            : "N/A";
    String projectName = fundAnIdea.getFundanideaprojectname() != null ? fundAnIdea.getFundanideaprojectname()
            : "N/A";
    String currentDate = new SimpleDateFormat("dd MMMM yyyy").format(new Date());

    return "<!DOCTYPE html>" +
            "<html lang='en'>" +
            "<head>" +
            "  <meta charset='UTF-8'>" +
            "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            "  <title>Fund an Idea Application Confirmation</title>" +
            "  <style>" +
            "    * { margin: 0; padding: 0; box-sizing: border-box; }" +
            "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; line-height: 1.6; }"
            +
            "    .email-container { max-width: 650px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); width: 95%; }"
            +
            "    .header { background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); padding: 30px 20px; text-align: center; }"
            +
            "    .logo-section { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; padding:20px; background-color:#ffffff;border-radius:15px; width: 100%; }"
            +
            "    .logo-left { display: flex; align-items: center; gap: 15px; flex: 1; }" +
            "    .logo-right { display: flex; align-items: center; justify-content: flex-end; margin-left: auto; margin-right: 10px; }"
            +
            "    .amc-logo { height: 60px; transition: transform 0.3s ease; }" +
            "    .amc-text { height: 40px; width: auto; margin-left: 15px; transition: transform 0.3s ease; }" +
            "    .gov-logo { height: 60px; transition: transform 0.3s ease; margin-left: 20px; }"
            +
            "    .logo:hover, .amc-logo:hover, .amc-text:hover, .gov-logo:hover { transform: scale(1.05); }"
            +
            "    .header-title { color: #ffffff; font-size: 28px; font-weight: 600; margin: 0; text-shadow: 0 2px 4px rgba(0,0,0,0.3); }"
            +
            "    .header-subtitle { color: #e8f4fd; font-size: 16px; margin-top: 8px; font-weight: 300; }" +
            "    .content { padding: 40px 30px; }" +
            "    .greeting { font-size: 20px; color: #2c3e50; margin-bottom: 20px; font-weight: 500; }" +
            "    .message { color: #555555; font-size: 16px; margin-bottom: 25px; }" +
            "    .project-details { background: #f8f9fa; border-radius: 10px; padding: 20px; margin: 25px 0; border-left: 5px solid #0b1e46; }"
            +
            "    .project-details h3 { color: #0b1e46; font-size: 18px; margin-bottom: 15px; display: flex; align-items: center; }"
            +
            "    .project-info { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-top: 15px; }" +
            "    .info-item { background: #ffffff; padding: 12px; border-radius: 6px; border: 1px solid #e9ecef; }"
            +
            "    .info-label { font-weight: 600; color: #495057; font-size: 14px; margin-bottom: 5px; }" +
            "    .info-value { color: #0b1e46; font-weight: 500; }" +
            "    .token-container { background: linear-gradient(135deg, #0b1e46 0%, #4dabf7 100%); border-radius: 12px; padding: 25px; margin: 30px 0; text-align: center; position: relative; overflow: hidden; }"
            +
            "    .token-container::before { content: ''; position: absolute; top: -50%; left: -50%; width: 200%; height: 200%; background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%); animation: shimmer 3s ease-in-out infinite; }"
            +
            "    .token-label { color: #ffffff; font-size: 16px; font-weight: 600; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 1px; }"
            +
            "    .token-value { color: #ffffff; font-size: 32px; font-weight: 700; font-family: 'Courier New', monospace; letter-spacing: 3px; text-shadow: 0 2px 4px rgba(0,0,0,0.3); position: relative; z-index: 1; }"
            +
            "    .copy-hint { color: #e3f2fd; font-size: 12px; margin-top: 10px; font-style: italic; }" +
            "    .instructions { background: #e3f2fd; border-left: 4px solid #0b1e46; padding: 20px; margin: 25px 0; border-radius: 0 8px 8px 0; }"
            +
            "    .instructions h3 { color: #0b1e46; font-size: 18px; margin-bottom: 10px; }" +
            "    .instructions ul { color: #555555; padding-left: 20px; }" +
            "    .instructions li { margin-bottom: 8px; }" +
            "    .next-steps { background: linear-gradient(135deg, #0d6efd 0%, #4dabf7 100%); color: #ffffff; border-radius: 10px; padding: 20px; margin: 25px 0; }"
            +
            "    .next-steps h3 { margin-bottom: 15px; }" +
            "    .steps-list { list-style: none; padding: 0; }" +
            "    .steps-list li { margin-bottom: 10px; padding-left: 30px; position: relative; }" +
            "    .steps-list li::before { content: '✓'; position: absolute; left: 0; top: 0; color: #ffffff; font-weight: bold; font-size: 16px; }"
            +
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
            "    .application-id { background: #e3f2fd; padding: 12px; border-radius: 6px; margin: 15px 0; text-align: center; border: 1px solid #0b1e46; }"
            +
            "    .application-id strong { color: #0b1e46; }" +
            "    .fund-badge { background: linear-gradient(135deg, #0b1e46 0%, #0d6efd 100%); color: #ffffff; padding: 8px 16px; border-radius: 20px; font-size: 14px; font-weight: 600; display: inline-block; margin-bottom: 15px; }"
            +
            "    @keyframes shimmer { 0%, 100% { transform: translateX(-100%); } 50% { transform: translateX(100%); } }"
            +
            "    @media (max-width: 768px) {" +
            "      .email-container { margin: 5px !important; border-radius: 8px !important; max-width: 100% !important; }"
            +
            "      .content { padding: 20px 15px !important; }" +
            "      .header { padding: 20px 15px !important; }" +
            "      .logo-section { flex-direction: row !important; justify-content: space-between !important; gap: 10px !important; padding: 15px !important; }"
            +
            "      .logo-left { flex: none !important; justify-content: flex-start !important; gap: 8px !important; }" +
            "      .logo-right { flex: none !important; justify-content: flex-end !important; margin-left: auto !important; margin-right: 8px !important; }"
            +
            "      .amc-logo { height: 35px !important; }" +
            "      .amc-text { height: 25px !important; width: auto !important; margin-left: 8px !important; }" +
            "      .gov-logo { height: 35px !important; margin-left: 10px !important; }" +
            "      .header-title { font-size: 22px !important; }" +
            "      .header-subtitle { font-size: 14px !important; }" +
            "      .greeting { font-size: 18px !important; }" +
            "      .message { font-size: 14px !important; }" +
            "      .project-info { grid-template-columns: 1fr !important; gap: 10px !important; }" +
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
            "      .next-steps { padding: 15px !important; }" +
            "      .project-details { padding: 15px !important; }" +
            "    }" +
            "    @media (max-width: 480px) {" +
            "      .logo-section { flex-direction: row !important; align-items: center !important; justify-content: space-between !important; padding: 10px !important; }"
            +
            "      .logo-left { gap: 5px !important; }" +
            "      .logo-right { margin-left: 10px !important; margin-right: 5px !important; }" +
            "      .amc-logo { height: 30px !important; }" +
            "      .amc-text { height: 20px !important; margin-left: 5px !important; }" +
            "      .gov-logo { height: 30px !important; margin-left: 8px !important; }" +
            "      .header-title { font-size: 20px !important; }" +
            "      .token-value { font-size: 18px !important; }" +
            "      .content { padding: 15px 10px !important; }" +
            "      .footer { padding: 15px 10px !important; }" +
            "    }" +
            "  </style>" +
            "</head>" +
            "<body>" +
            "  <div class='email-container'>" +
            "    <div class='header'>" +
            "      <div class='logo-section'>" +
            "        <div class='logo-left'>" +
            "          <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amcLogo.png' class='amc-logo' alt='AMC Logo'>"
            +
            "          <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amc%20logo.png' style='height: 45px;  width: 200px; margin-top: 10px;margin-left: 10px;' class='amc-text' alt='AMC Name'>"
            +
            "        </div>" +
            "        <div class='logo-right'>" +
            "          <img src='https://gcsra.org/writereaddata/Images/gov-gujarat-logo.png' class='gov-logo' alt='Gujarat Government Logo'>"
            +
            "        </div>" +
            "      </div>" +
            "      <div class='fund-badge'>💡 Fund an Idea Initiative</div>" +
            "      <h1 class='header-title'>Application Submitted Successfully</h1>" +
            "      <p class='header-subtitle'>Innovation & Development Program</p>" +
            "    </div>" +
            "" +
            "    <div class='content'>" +
            "      <div class='greeting'>Dear " + name + ",</div>" +
            "" +
            "      <div class='message'>" +
            "        Thank you for submitting your innovative idea through our 'Fund an Idea' initiative! " +
            "        We are excited to review your proposal and appreciate your commitment to community development and innovation. "
            +
            "        Your application has been successfully received and is now in our review process." +
            "      </div>" +
            "" +
            "      <div class='application-id'>" +
            "        <strong>📅 Application Submitted:</strong> " + currentDate +
            "      </div>" +
            "" +
            "      <div class='project-details'>" +
            "        <h3>🚀 Project Summary</h3>" +
            "        <div class='project-info'>" +
            "          <div class='info-item'>" +
            "            <div class='info-label'>Organization</div>" +
            "            <div class='info-value'>" + organizationName + "</div>" +
            "          </div>" +
            "          <div class='info-item'>" +
            "            <div class='info-label'>Project Name</div>" +
            "            <div class='info-value'>" + projectName + "</div>" +
            "          </div>" +
            "        </div>" +
            "      </div>" +
            "" +
            "      <div class='token-container'>" +
            "        <div class='token-label'>🎫 Your Unique Application ID</div>" +
            "        <div class='token-value'>" + token + "</div>" +
            "        <div class='copy-hint'>Save this ID for tracking and future reference</div>" +
            "      </div>" +
            "" +
            "      <div class='next-steps'>" +
            "        <h3>📋 What Happens Next?</h3>" +
            "        <ul class='steps-list'>" +
            "          <li>Our expert panel will review your application within 7-14 working days</li>" +
            "          <li>You'll receive updates on your application status via email</li>" +
            "          <li>If shortlisted, you may be invited for a presentation/interview</li>" +
            "          <li>Final funding decisions will be communicated within 30 days</li>" +
            "        </ul>" +
            "      </div>" +
            "" +
            "      <div class='instructions'>" +
            "        <h3>📝 Important Guidelines:</h3>" +
            "        <ul>" +
            "          <li><strong>Application ID:</strong> Use this ID for all future communications and inquiries</li>"
            +
            "          <li><strong>Status Updates:</strong> You'll receive email notifications at key review stages</li>"
            +
            "          <li><strong>Additional Documents:</strong> If required, we'll contact you for supplementary materials</li>"
            +
            "          <li><strong>Contact Support:</strong> For urgent queries, use the contact information provided below</li>"
            +
            "        </ul>" +
            "      </div>" +
            "" +
            "      <div class='warning'>" +
            "        <span class='warning-icon'>🔒</span>" +
            "        <span class='warning-text'>" +
            "          <strong>Confidentiality Notice:</strong> Your application ID is confidential. " +
            "          Please keep it secure and only share with authorized personnel when required. " +
            "          Report any unauthorized use immediately." +
            "        </span>" +
            "      </div>" +
            "" +
            "      <div class='divider'></div>" +
            "" +
            "      <div class='message'>" +
            "        We believe in the power of innovative ideas to transform communities. " +
            "        Your submission is valued, and we're committed to providing a fair and transparent review process. "
            +
            "        Thank you for being part of our mission to foster innovation and social impact." +
            "      </div>" +
            "    </div>" +
            "" +
            "    <div class='footer'>" +
            "      <div class='footer-content'>" +
            "        <strong>Best regards,</strong><br>" +
            "        <strong>Ahmedabad Municipal Corporation</strong><br>" +
            "        Fund an Idea - Innovation Department" +
            "      </div>" +
            "" +
            "      <div class='contact-info'>" +
            "        <h4>📞 Need Help? Contact Us</h4>" +
            "        <div class='contact-details'>" +
            "          <strong>📧 Email:</strong> ccrs@ahmedabadcity.gov.in<br>" +
            "          <strong>🏢 Office:</strong> Ahmedabad Municipal Corporation<br>" +
            "          <strong>📍 Address:</strong> Dr. Karmavir Bhaurao Patil Bhavan, Usmanpura, Ahmedabad - 380013<br>"
            +
            "          <strong>🕒 Working Hours:</strong> Monday to Friday, 10:30 AM to 6:00 PM<br>" +
            "          <strong>💡 Program Website:</strong> www.ahmedabadcity.gov.in/fund-an-idea" +
            "        </div>" +
            "      </div>" +
            "" +
            "      <div style='margin-top: 20px; font-size: 12px; color: #bdc3c7;'>" +
            "        This is an automated confirmation email. Please do not reply directly to this message.<br>" +
            "        For support, use the contact information provided above.<br>" +
            "        © " + java.time.Year.now() + " Ahmedabad Municipal Corporation. All rights reserved." +
            "      </div>" +
            "    </div>" +
            "  </div>" +
            "</body>" +
            "</html>";
}
    // private String createEmailBody(FundAnIdea fundAnIdea) {
    //     String name = fundAnIdea.getFundanideacontactpersonname() != null ? fundAnIdea.getFundanideacontactpersonname()
    //             : (fundAnIdea.getFundanideaemailid() != null ? fundAnIdea.getFundanideaemailid() : "Applicant");
    //     String token = fundAnIdea.getFundanideatoken() != null ? fundAnIdea.getFundanideatoken() : "N/A";
    //     String organizationName = fundAnIdea.getFundanideaorganizationname() != null
    //             ? fundAnIdea.getFundanideaorganizationname()
    //             : "N/A";
    //     String projectName = fundAnIdea.getFundanideaprojectname() != null ? fundAnIdea.getFundanideaprojectname()
    //             : "N/A";
    //     String currentDate = new SimpleDateFormat("dd MMMM yyyy").format(new Date());

    //     return "<!DOCTYPE html>" +
    //             "<html lang='en'>" +
    //             "<head>" +
    //             "  <meta charset='UTF-8'>" +
    //             "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
    //             "  <title>Fund an Idea Application Confirmation</title>" +
    //             "  <style>" +
    //             "    * { margin: 0; padding: 0; box-sizing: border-box; }" +
    //             "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; line-height: 1.6; }"
    //             +
    //             "    .email-container { max-width: 650px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); width: 95%; }"
    //             +
    //             "    .header { background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); padding: 30px 20px; text-align: center; }"
    //             +
    //             "    .logo-section { display: flex; justify-content: center; align-items: center; gap: 15px; margin-bottom: 20px; padding: 20px; background-color: #ffffff; border-radius: 15px; flex-wrap: wrap; }"
    //             +
    //             "    .logo { height: 60px; transition: transform 0.3s ease; max-width: 100%; object-fit: contain; }" +
    //             "    .logo:hover { transform: scale(1.05); }" +
    //             "    .header-title { color: #ffffff; font-size: 28px; font-weight: 600; margin: 0; text-shadow: 0 2px 4px rgba(0,0,0,0.3); }"
    //             +
    //             "    .header-subtitle { color: #e8f4fd; font-size: 16px; margin-top: 8px; font-weight: 300; }" +
    //             "    .content { padding: 40px 30px; }" +
    //             "    .greeting { font-size: 20px; color: #2c3e50; margin-bottom: 20px; font-weight: 500; }" +
    //             "    .message { color: #555555; font-size: 16px; margin-bottom: 25px; }" +
    //             "    .project-details { background: #f8f9fa; border-radius: 10px; padding: 20px; margin: 25px 0; border-left: 5px solid #0b1e46; }"
    //             +
    //             "    .project-details h3 { color: #0b1e46; font-size: 18px; margin-bottom: 15px; display: flex; align-items: center; }"
    //             +
    //             "    .project-info { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-top: 15px; }" +
    //             "    .info-item { background: #ffffff; padding: 12px; border-radius: 6px; border: 1px solid #e9ecef; }"
    //             +
    //             "    .info-label { font-weight: 600; color: #495057; font-size: 14px; margin-bottom: 5px; }" +
    //             "    .info-value { color: #0b1e46; font-weight: 500; }" +
    //             "    .token-container { background: linear-gradient(135deg, #0b1e46 0%, #4dabf7 100%); border-radius: 12px; padding: 25px; margin: 30px 0; text-align: center; position: relative; overflow: hidden; }"
    //             +
    //             "    .token-container::before { content: ''; position: absolute; top: -50%; left: -50%; width: 200%; height: 200%; background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%); animation: shimmer 3s ease-in-out infinite; }"
    //             +
    //             "    .token-label { color: #ffffff; font-size: 16px; font-weight: 600; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 1px; }"
    //             +
    //             "    .token-value { color: #ffffff; font-size: 32px; font-weight: 700; font-family: 'Courier New', monospace; letter-spacing: 3px; text-shadow: 0 2px 4px rgba(0,0,0,0.3); position: relative; z-index: 1; }"
    //             +
    //             "    .copy-hint { color: #e3f2fd; font-size: 12px; margin-top: 10px; font-style: italic; }" +
    //             "    .instructions { background: #e3f2fd; border-left: 4px solid #0b1e46; padding: 20px; margin: 25px 0; border-radius: 0 8px 8px 0; }"
    //             +
    //             "    .instructions h3 { color: #0b1e46; font-size: 18px; margin-bottom: 10px; }" +
    //             "    .instructions ul { color: #555555; padding-left: 20px; }" +
    //             "    .instructions li { margin-bottom: 8px; }" +
    //             "    .next-steps { background: linear-gradient(135deg, #0d6efd 0%, #4dabf7 100%); color: #ffffff; border-radius: 10px; padding: 20px; margin: 25px 0; }"
    //             +
    //             "    .next-steps h3 { margin-bottom: 15px; }" +
    //             "    .steps-list { list-style: none; padding: 0; }" +
    //             "    .steps-list li { margin-bottom: 10px; padding-left: 30px; position: relative; }" +
    //             "    .steps-list li::before { content: '✓'; position: absolute; left: 0; top: 0; color: #ffffff; font-weight: bold; font-size: 16px; }"
    //             +
    //             "    .warning { background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 8px; padding: 15px; margin: 20px 0; display: flex; align-items: center; }"
    //             +
    //             "    .warning-icon { color: #f39c12; font-size: 20px; margin-right: 10px; }" +
    //             "    .warning-text { color: #856404; font-weight: 500; }" +
    //             "    .footer { background: #2c3e50; color: #ffffff; padding: 30px; text-align: center; }" +
    //             "    .footer-content { margin-bottom: 20px; }" +
    //             "    .contact-info { background: rgba(255,255,255,0.1); border-radius: 8px; padding: 15px; margin-top: 20px; }"
    //             +
    //             "    .contact-info h4 { margin-bottom: 10px; color: #ecf0f1; }" +
    //             "    .contact-details { font-size: 14px; line-height: 1.8; }" +
    //             "    .divider { height: 1px; background: linear-gradient(to right, transparent, #bdc3c7, transparent); margin: 25px 0; }"
    //             +
    //             "    .application-id { background: #e3f2fd; padding: 12px; border-radius: 6px; margin: 15px 0; text-align: center; border: 1px solid #0b1e46; }"
    //             +
    //             "    .application-id strong { color: #0b1e46; }" +
    //             "    .fund-badge { background: linear-gradient(135deg, #0b1e46 0%, #0d6efd 100%); color: #ffffff; padding: 8px 16px; border-radius: 20px; font-size: 14px; font-weight: 600; display: inline-block; margin-bottom: 15px; }"
    //             +
    //             "    @keyframes shimmer { 0%, 100% { transform: translateX(-100%); } 50% { transform: translateX(100%); } }"
    //             +
    //             "    @media (max-width: 768px) {" +
    //             "      .email-container { margin: 5px !important; border-radius: 8px !important; max-width: 100% !important; }"
    //             +
    //             "      .content { padding: 20px 15px !important; }" +
    //             "      .header { padding: 20px 15px !important; }" +
    //             "      .logo-section { flex-direction: row !important; flex-wrap: wrap !important; gap: 8px !important; padding: 15px !important; }"
    //             +
    //             "      .logo { height: 45px !important; max-width: 80px !important; }" +
    //             "      .header-title { font-size: 22px !important; }" +
    //             "      .header-subtitle { font-size: 14px !important; }" +
    //             "      .greeting { font-size: 18px !important; }" +
    //             "      .message { font-size: 14px !important; }" +
    //             "      .project-info { grid-template-columns: 1fr !important; gap: 10px !important; }" +
    //             "      .token-container { padding: 20px 15px !important; margin: 20px 0 !important; }" +
    //             "      .token-value { font-size: 20px !important; letter-spacing: 2px !important; word-break: break-all !important; }"
    //             +
    //             "      .token-label { font-size: 14px !important; }" +
    //             "      .instructions { padding: 15px !important; margin: 20px 0 !important; }" +
    //             "      .instructions h3 { font-size: 16px !important; }" +
    //             "      .instructions ul { padding-left: 15px !important; }" +
    //             "      .instructions li { font-size: 14px !important; }" +
    //             "      .warning { padding: 12px !important; flex-direction: column !important; text-align: center !important; }"
    //             +
    //             "      .warning-icon { margin-right: 0 !important; margin-bottom: 5px !important; }" +
    //             "      .warning-text { font-size: 14px !important; }" +
    //             "      .footer { padding: 20px 15px !important; }" +
    //             "      .contact-info { padding: 12px !important; }" +
    //             "      .contact-details { font-size: 12px !important; }" +
    //             "      .application-id { padding: 10px !important; }" +
    //             "      .next-steps { padding: 15px !important; }" +
    //             "      .project-details { padding: 15px !important; }" +
    //             "    }" +
    //             "    @media (max-width: 480px) {" +
    //             "      .logo-section { flex-direction: column !important; align-items: center !important; }" +
    //             "      .logo { height: 40px !important; }" +
    //             "      .header-title { font-size: 20px !important; }" +
    //             "      .token-value { font-size: 18px !important; }" +
    //             "      .content { padding: 15px 10px !important; }" +
    //             "      .footer { padding: 15px 10px !important; }" +
    //             "    }" +
    //             "    .logo-section img:nth-child(2) { max-width: 150px !important; height: auto !important; }" +
    //             "  </style>" +
    //             "</head>" +
    //             "<body>" +
    //             "  <div class='email-container'>" +
    //             "    <div class='header'>" +
    //             "      <div class='logo-section'>" +
    //             "        <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amcLogo.png' class='logo' alt='AMC Logo'>"
    //             +
    //             "        <img src='https://lakhpatididi.in/SRS-Ahembdabad/images/amc%20logo.png' class='logo' style='height: 45px;width: 200px;margin-top: 10px;margin-left: 10px;' alt='AMC Name'>"
    //             +
    //             "        <img src='https://gcsra.org/writereaddata/Images/gov-gujarat-logo.png' class='logo' alt='Gujarat Government Logo'>"
    //             +
    //             "      </div>" +
    //             "      <div class='fund-badge'>💡 Fund an Idea Initiative</div>" +
    //             "      <h1 class='header-title'>Application Submitted Successfully</h1>" +
    //             "      <p class='header-subtitle'>Innovation & Development Program</p>" +
    //             "    </div>" +
    //             "" +
    //             "    <div class='content'>" +
    //             "      <div class='greeting'>Dear " + name + ",</div>" +
    //             "" +
    //             "      <div class='message'>" +
    //             "        Thank you for submitting your innovative idea through our 'Fund an Idea' initiative! " +
    //             "        We are excited to review your proposal and appreciate your commitment to community development and innovation. "
    //             +
    //             "        Your application has been successfully received and is now in our review process." +
    //             "      </div>" +
    //             "" +
    //             "      <div class='application-id'>" +
    //             "        <strong>📅 Application Submitted:</strong> " + currentDate +
    //             "      </div>" +
    //             "" +
    //             "      <div class='project-details'>" +
    //             "        <h3>🚀 Project Summary</h3>" +
    //             "        <div class='project-info'>" +
    //             "          <div class='info-item'>" +
    //             "            <div class='info-label'>Organization</div>" +
    //             "            <div class='info-value'>" + organizationName + "</div>" +
    //             "          </div>" +
    //             "          <div class='info-item'>" +
    //             "            <div class='info-label'>Project Name</div>" +
    //             "            <div class='info-value'>" + projectName + "</div>" +
    //             "          </div>" +
    //             "        </div>" +
    //             "      </div>" +
    //             "" +
    //             "      <div class='token-container'>" +
    //             "        <div class='token-label'>🎫 Your Unique Application ID</div>" +
    //             "        <div class='token-value'>" + token + "</div>" +
    //             "        <div class='copy-hint'>Save this ID for tracking and future reference</div>" +
    //             "      </div>" +
    //             "" +
    //             "      <div class='next-steps'>" +
    //             "        <h3>📋 What Happens Next?</h3>" +
    //             "        <ul class='steps-list'>" +
    //             "          <li>Our expert panel will review your application within 7-14 working days</li>" +
    //             "          <li>You'll receive updates on your application status via email</li>" +
    //             "          <li>If shortlisted, you may be invited for a presentation/interview</li>" +
    //             "          <li>Final funding decisions will be communicated within 30 days</li>" +
    //             "        </ul>" +
    //             "      </div>" +
    //             "" +
    //             "      <div class='instructions'>" +
    //             "        <h3>📝 Important Guidelines:</h3>" +
    //             "        <ul>" +
    //             "          <li><strong>Application ID:</strong> Use this ID for all future communications and inquiries</li>"
    //             +
    //             "          <li><strong>Status Updates:</strong> You'll receive email notifications at key review stages</li>"
    //             +
    //             "          <li><strong>Additional Documents:</strong> If required, we'll contact you for supplementary materials</li>"
    //             +
    //             "          <li><strong>Contact Support:</strong> For urgent queries, use the contact information provided below</li>"
    //             +
    //             "        </ul>" +
    //             "      </div>" +
    //             "" +
    //             "      <div class='warning'>" +
    //             "        <span class='warning-icon'>🔒</span>" +
    //             "        <span class='warning-text'>" +
    //             "          <strong>Confidentiality Notice:</strong> Your application ID is confidential. " +
    //             "          Please keep it secure and only share with authorized personnel when required. " +
    //             "          Report any unauthorized use immediately." +
    //             "        </span>" +
    //             "      </div>" +
    //             "" +
    //             "      <div class='divider'></div>" +
    //             "" +
    //             "      <div class='message'>" +
    //             "        We believe in the power of innovative ideas to transform communities. " +
    //             "        Your submission is valued, and we're committed to providing a fair and transparent review process. "
    //             +
    //             "        Thank you for being part of our mission to foster innovation and social impact." +
    //             "      </div>" +
    //             "    </div>" +
    //             "" +
    //             "    <div class='footer'>" +
    //             "      <div class='footer-content'>" +
    //             "        <strong>Best regards,</strong><br>" +
    //             "        <strong>Ahmedabad Municipal Corporation</strong><br>" +
    //             "        Fund an Idea - Innovation Department" +
    //             "      </div>" +
    //             "" +
    //             "      <div class='contact-info'>" +
    //             "        <h4>📞 Need Help? Contact Us</h4>" +
    //             "        <div class='contact-details'>" +
    //             "          <strong>📧 Email:</strong> ccrs@ahmedabadcity.gov.in<br>" +
    //             "          <strong>🏢 Office:</strong> Ahmedabad Municipal Corporation<br>" +
    //             "          <strong>📍 Address:</strong> Dr. Karmavir Bhaurao Patil Bhavan, Usmanpura, Ahmedabad - 380013<br>"
    //             +
    //             "          <strong>🕒 Working Hours:</strong> Monday to Friday, 10:30 AM to 6:00 PM<br>" +
    //             "          <strong>💡 Program Website:</strong> www.ahmedabadcity.gov.in/fund-an-idea" +
    //             "        </div>" +
    //             "      </div>" +
    //             "" +
    //             "      <div style='margin-top: 20px; font-size: 12px; color: #bdc3c7;'>" +
    //             "        This is an automated confirmation email. Please do not reply directly to this message.<br>" +
    //             "        For support, use the contact information provided above.<br>" +
    //             "        © " + java.time.Year.now() + " Ahmedabad Municipal Corporation. All rights reserved." +
    //             "      </div>" +
    //             "    </div>" +
    //             "  </div>" +
    //             "</body>" +
    //             "</html>";
    // }
   
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
