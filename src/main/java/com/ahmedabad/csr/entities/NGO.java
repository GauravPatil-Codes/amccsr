package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ngos")
public class NGO {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String organizationName;
    private String emailId;
    private String userName;
    private String password;
    private String ageOfOrganization;
    private String annualTurnover;
    private String nameOfContactPerson;
    private String contactNumber;
    private String ngo80GregistrationNumber;
    private String ngo80Gdocument;
    private String ngo12AregistrationNumber;
    private String ngo12Adocument;
    private String caCertifiedStatementUpload;
    private String organizationRegistrationCertificate;
    private String csr1RegistartionNumber;
    private String csr1Document;
    private String bylaws;
    private String moa;
    private String captchaCode;
    private String status;

    // Default constructor
    public NGO() {
    }

    // Constructor with all fields
    public NGO(int id, String organizationName, String emailId, String userName, String password,
            String ageOfOrganization, String annualTurnover, String nameOfContactPerson, String contactNumber,
            String ngo80GregistrationNumber, String ngo80Gdocument, String ngo12AregistrationNumber,
            String ngo12Adocument, String caCertifiedStatementUpload, String organizationRegistrationCertificate,
            String csr1RegistartionNumber, String csr1Document, String bylaws, String moa, String captchaCode,
            String status) {
        this.id = id;
        this.organizationName = organizationName;
        this.emailId = emailId;
        this.userName = userName;
        this.password = password;
        this.ageOfOrganization = ageOfOrganization;
        this.annualTurnover = annualTurnover;
        this.nameOfContactPerson = nameOfContactPerson;
        this.contactNumber = contactNumber;
        this.ngo80GregistrationNumber = ngo80GregistrationNumber;
        this.ngo80Gdocument = ngo80Gdocument;
        this.ngo12AregistrationNumber = ngo12AregistrationNumber;
        this.ngo12Adocument = ngo12Adocument;
        this.caCertifiedStatementUpload = caCertifiedStatementUpload;
        this.organizationRegistrationCertificate = organizationRegistrationCertificate;
        this.csr1RegistartionNumber = csr1RegistartionNumber;
        this.csr1Document = csr1Document;
        this.bylaws = bylaws;
        this.moa = moa;
        this.captchaCode = captchaCode;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgeOfOrganization() {
        return ageOfOrganization;
    }

    public void setAgeOfOrganization(String ageOfOrganization) {
        this.ageOfOrganization = ageOfOrganization;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getNameOfContactPerson() {
        return nameOfContactPerson;
    }

    public void setNameOfContactPerson(String nameOfContactPerson) {
        this.nameOfContactPerson = nameOfContactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNgo80GregistrationNumber() {
        return ngo80GregistrationNumber;
    }

    public void setNgo80GregistrationNumber(String ngo80GregistrationNumber) {
        this.ngo80GregistrationNumber = ngo80GregistrationNumber;
    }

    public String getNgo80Gdocument() {
        return ngo80Gdocument;
    }

    public void setNgo80Gdocument(String ngo80Gdocument) {
        this.ngo80Gdocument = ngo80Gdocument;
    }

    public String getNgo12AregistrationNumber() {
        return ngo12AregistrationNumber;
    }

    public void setNgo12AregistrationNumber(String ngo12AregistrationNumber) {
        this.ngo12AregistrationNumber = ngo12AregistrationNumber;
    }

    public String getNgo12Adocument() {
        return ngo12Adocument;
    }

    public void setNgo12Adocument(String ngo12Adocument) {
        this.ngo12Adocument = ngo12Adocument;
    }

    public String getCaCertifiedStatementUpload() {
        return caCertifiedStatementUpload;
    }

    public void setCaCertifiedStatementUpload(String caCertifiedStatementUpload) {
        this.caCertifiedStatementUpload = caCertifiedStatementUpload;
    }

    public String getOrganizationRegistrationCertificate() {
        return organizationRegistrationCertificate;
    }

    public void setOrganizationRegistrationCertificate(String organizationRegistrationCertificate) {
        this.organizationRegistrationCertificate = organizationRegistrationCertificate;
    }

    public String getCsr1RegistartionNumber() {
        return csr1RegistartionNumber;
    }

    public void setCsr1RegistartionNumber(String csr1RegistartionNumber) {
        this.csr1RegistartionNumber = csr1RegistartionNumber;
    }

    public String getCsr1Document() {
        return csr1Document;
    }

    public void setCsr1Document(String csr1Document) {
        this.csr1Document = csr1Document;
    }

    public String getBylaws() {
        return bylaws;
    }

    public void setBylaws(String bylaws) {
        this.bylaws = bylaws;
    }

    public String getMoa() {
        return moa;
    }

    public void setMoa(String moa) {
        this.moa = moa;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NGO [id=" + id + ", organizationName=" + organizationName + ", emailId=" + emailId + ", userName="
                + userName + ", password=" + password + ", ageOfOrganization=" + ageOfOrganization + ", annualTurnover="
                + annualTurnover + ", nameOfContactPerson=" + nameOfContactPerson + ", contactNumber=" + contactNumber
                + ", ngo80GregistrationNumber=" + ngo80GregistrationNumber + ", ngo80Gdocument=" + ngo80Gdocument
                + ", ngo12AregistrationNumber=" + ngo12AregistrationNumber + ", ngo12Adocument=" + ngo12Adocument
                + ", caCertifiedStatementUpload=" + caCertifiedStatementUpload
                + ", organizationRegistrationCertificate=" + organizationRegistrationCertificate
                + ", csr1RegistartionNumber=" + csr1RegistartionNumber + ", csr1Document=" + csr1Document + ", bylaws="
                + bylaws + ", moa=" + moa + ", captchaCode=" + captchaCode + ", status=" + status + "]";
    }
}