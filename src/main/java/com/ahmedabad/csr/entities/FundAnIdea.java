package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fundanidea")
public class FundAnIdea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fundanideaid;
    private String natureofproject;
    private String fundanideaprojectname;
    private String fundanideaprojectlocation;
    private String fundanideadepartment;
    private String fundanideadocement;
    private String fundanideadescription;
    private String fundanideaorganizationname;
    private String fundanideaemailid;
    private String fundanideaphonenumber;
    private String fundanideacontactpersonname;
    private String fundanideaestimateamount;
    private String fundanideastatus;

    public FundAnIdea() {
    }

    public int getFundanideaid() {
        return fundanideaid;
    }

    public void setFundanideaid(int fundanideaid) {
        this.fundanideaid = fundanideaid;
    }

    public String getNatureofproject() {
        return natureofproject;
    }

    public void setNatureofproject(String natureofproject) {
        this.natureofproject = natureofproject;
    }

    public String getFundanideaprojectname() {
        return fundanideaprojectname;
    }

    public void setFundanideaprojectname(String fundanideaprojectname) {
        this.fundanideaprojectname = fundanideaprojectname;
    }

    public String getFundanideaprojectlocation() {
        return fundanideaprojectlocation;
    }

    public void setFundanideaprojectlocation(String fundanideaprojectlocation) {
        this.fundanideaprojectlocation = fundanideaprojectlocation;
    }

    public String getFundanideadepartment() {
        return fundanideadepartment;
    }

    public void setFundanideadepartment(String fundanideadepartment) {
        this.fundanideadepartment = fundanideadepartment;
    }

    public String getFundanideadocement() {
        return fundanideadocement;
    }

    public void setFundanideadocement(String fundanideadocement) {
        this.fundanideadocement = fundanideadocement;
    }

    public String getFundanideadescription() {
        return fundanideadescription;
    }

    public void setFundanideadescription(String fundanideadescription) {
        this.fundanideadescription = fundanideadescription;
    }

    public String getFundanideaorganizationname() {
        return fundanideaorganizationname;
    }

    public void setFundanideaorganizationname(String fundanideaorganizationname) {
        this.fundanideaorganizationname = fundanideaorganizationname;
    }

    public String getFundanideaemailid() {
        return fundanideaemailid;
    }

    public void setFundanideaemailid(String fundanideaemailid) {
        this.fundanideaemailid = fundanideaemailid;
    }

    public String getFundanideaphonenumber() {
        return fundanideaphonenumber;
    }

    public void setFundanideaphonenumber(String fundanideaphonenumber) {
        this.fundanideaphonenumber = fundanideaphonenumber;
    }

    public String getFundanideacontactpersonname() {
        return fundanideacontactpersonname;
    }

    public void setFundanideacontactpersonname(String fundanideacontactpersonname) {
        this.fundanideacontactpersonname = fundanideacontactpersonname;
    }

    public String getFundanideaestimateamount() {
        return fundanideaestimateamount;
    }

    public void setFundanideaestimateamount(String fundanideaestimateamount) {
        this.fundanideaestimateamount = fundanideaestimateamount;
    }

    public String getFundanideastatus() {
        return fundanideastatus;
    }

    public void setFundanideastatus(String fundanideastatus) {
        this.fundanideastatus = fundanideastatus;
    }

    public FundAnIdea(int fundanideaid, String natureofproject, String fundanideaprojectname,
            String fundanideaprojectlocation, String fundanideadepartment, String fundanideadocement,
            String fundanideadescription, String fundanideaorganizationname, String fundanideaemailid,
            String fundanideaphonenumber, String fundanideacontactpersonname, String fundanideaestimateamount,
            String fundanideastatus) {
        this.fundanideaid = fundanideaid;
        this.natureofproject = natureofproject;
        this.fundanideaprojectname = fundanideaprojectname;
        this.fundanideaprojectlocation = fundanideaprojectlocation;
        this.fundanideadepartment = fundanideadepartment;
        this.fundanideadocement = fundanideadocement;
        this.fundanideadescription = fundanideadescription;
        this.fundanideaorganizationname = fundanideaorganizationname;
        this.fundanideaemailid = fundanideaemailid;
        this.fundanideaphonenumber = fundanideaphonenumber;
        this.fundanideacontactpersonname = fundanideacontactpersonname;
        this.fundanideaestimateamount = fundanideaestimateamount;
        this.fundanideastatus = fundanideastatus;
    }

    @Override
    public String toString() {
        return "FundAnIdea [fundanideaid=" + fundanideaid + ", natureofproject=" + natureofproject
                + ", fundanideaprojectname=" + fundanideaprojectname + ", fundanideaprojectlocation="
                + fundanideaprojectlocation + ", fundanideadepartment=" + fundanideadepartment + ", fundanideadocement="
                + fundanideadocement + ", fundanideadescription=" + fundanideadescription
                + ", fundanideaorganizationname=" + fundanideaorganizationname + ", fundanideaemailid="
                + fundanideaemailid + ", fundanideaphonenumber=" + fundanideaphonenumber
                + ", fundanideacontactpersonname=" + fundanideacontactpersonname + ", fundanideaestimateamount="
                + fundanideaestimateamount + ", fundanideastatus=" + fundanideastatus + "]";
    }

}