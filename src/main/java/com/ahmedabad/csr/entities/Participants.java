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
    private String participantName;
    private String OrganizationName;
    private String participantEmail;
    private String participantMobileNumber;
    private int amount;

    public Participants() {

    }

    @Override
    public String toString() {
        return "Participants [participantID=" + participantID + ", participantName=" + participantName
                + ", OrganizationName=" + OrganizationName + ", participantEmail=" + participantEmail
                + ", participantMobileNumber=" + participantMobileNumber + ", amount=" + amount + "]";
    }

    public Participants(int participantID, String participantName, String organizationName, String participantEmail,
            String participantMobileNumber, int amount) {
        this.participantID = participantID;
        this.participantName = participantName;
        OrganizationName = organizationName;
        this.participantEmail = participantEmail;
        this.participantMobileNumber = participantMobileNumber;
        this.amount = amount;
    }

    public int getParticipantID() {
        return participantID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
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

}
