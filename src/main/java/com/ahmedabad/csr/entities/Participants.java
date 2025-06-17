package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "participants ")
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantID;
    private int projetcId;
    private String participantName;
    private String OrganizationName;
    private String participantEmail;
    private String participantMobileNumber;
    private int amount;
    private String token;
    private String status;
    private String note;
    public Participants() {

    }
    public int getParticipantID() {
        return participantID;
    }
    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }
    public int getProjetcId() {
        return projetcId;
    }
    public void setProjetcId(int projetcId) {
        this.projetcId = projetcId;
    }
    public String getParticipantName() {
        return participantName;
    }
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
    public String getOrganizationName() {
        return OrganizationName;
    }
    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }
    public String getParticipantEmail() {
        return participantEmail;
    }
    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }
    public String getParticipantMobileNumber() {
        return participantMobileNumber;
    }
    public void setParticipantMobileNumber(String participantMobileNumber) {
        this.participantMobileNumber = participantMobileNumber;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Participants(int participantID, int projetcId, String participantName, String organizationName,
            String participantEmail, String participantMobileNumber, int amount, String token, String status,
            String note) {
        this.participantID = participantID;
        this.projetcId = projetcId;
        this.participantName = participantName;
        OrganizationName = organizationName;
        this.participantEmail = participantEmail;
        this.participantMobileNumber = participantMobileNumber;
        this.amount = amount;
        this.token = token;
        this.status = status;
        this.note = note;
    }
    @Override
    public String toString() {
        return "Participants [participantID=" + participantID + ", projetcId=" + projetcId + ", participantName="
                + participantName + ", OrganizationName=" + OrganizationName + ", participantEmail=" + participantEmail
                + ", participantMobileNumber=" + participantMobileNumber + ", amount=" + amount + ", token=" + token
                + ", status=" + status + ", note=" + note + "]";
    }

}