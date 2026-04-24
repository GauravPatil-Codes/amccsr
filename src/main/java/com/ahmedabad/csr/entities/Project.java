package com.ahmedabad.csr.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projetcId;
    private String projetcName;
    private String projetcDescription;
    private String projectStatus;
    private int ngoId;
    private int categoryId;
    private int companieId;
    private String projectMainImage;
    private String projectBudget;
    private String projectLocation;
    private String impactpeople;
    private String projectShortDescription;

    private String projectDEpartmentName;

    // COMMON
    private String theme;
    private String companyName;

    private Double totalProjectCost;
    private Double csrFundingAmount;

    private java.time.LocalDate mouSignedDate;
    private java.time.LocalDate completionDate;

    // ONLY FOR ONGOING
    private String financialProgress;
    private String physicalProgress;
    @ElementCollection
    private List<String> projectImages;

    
    // default constructor
    public Project() {

    }

   @Override
public String toString() {
    return "Project [projetcId=" + projetcId 
            + ", projetcName=" + projetcName 
            + ", projetcDescription=" + projetcDescription
            + ", projectStatus=" + projectStatus 
            + ", ngoId=" + ngoId 
            + ", categoryId=" + categoryId
            + ", projectMainImage=" + projectMainImage 
            + ", projectBudget=" + projectBudget
            + ", projectLocation=" + projectLocation 
            + ", impactpeople=" + impactpeople
            + ", projectShortDescription=" + projectShortDescription 
            + ", projectDEpartmentName=" + projectDEpartmentName
            + ", companieId=" + companieId
            + ", theme=" + theme 
            + ", companyName=" + companyName 
            + ", totalProjectCost=" + totalProjectCost
            + ", csrFundingAmount=" + csrFundingAmount
            + ", mouSignedDate=" + mouSignedDate
            + ", completionDate=" + completionDate
            + ", physicalProgress=" + physicalProgress
            + ", financialProgress=" + financialProgress
            + ", projectImages=" + projectImages
            + "]";
}
    public int getProjectId() {
        return projetcId;
    }

    public void setProjectId(int projetcId) {
        this.projetcId = projetcId;
    }

    public Project(int projetcId, String projetcName, String projetcDescription, String projectStatus, int ngoId,
            int categoryId, String projectMainImage, String projectBudget, String projectLocation,
            String impactpeople, String projectShortDescription, String projectDEpartmentName,
            List<String> projectImages,int companieId) {
        this.projetcId = projetcId;
        this.projetcName = projetcName;
        this.projetcDescription = projetcDescription;
        this.projectStatus = projectStatus;
        this.ngoId = ngoId;
        this.categoryId = categoryId;
        this.projectMainImage = projectMainImage;
        this.projectBudget = projectBudget;
        this.projectLocation = projectLocation;
        this.impactpeople = impactpeople;
        this.projectShortDescription = projectShortDescription;
        this.projectDEpartmentName = projectDEpartmentName;
        this.projectImages = projectImages;
        this.companieId=companieId;
    }

    public String getProjectName() {
        return projetcName;
    }

    public void setProjectName(String projetcName) {
        this.projetcName = projetcName;
    }

    public String getProjectDescription() {
        return projetcDescription;
    }

    public void setProjectDescription(String projetcDescription) {
        this.projetcDescription = projetcDescription;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public int getNgoId() {
        return ngoId;
    }

    public void setNgoId(int ngoId) {
        this.ngoId = ngoId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProjectMainImage() {
        return projectMainImage;
    }

    public void setProjectMainImage(String projectMainImage) {
        this.projectMainImage = projectMainImage;
    }

    public String getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(String projectBudget) {
        this.projectBudget = projectBudget;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getImpactpeople() {
        return impactpeople;
    }

    public void setImpactpeople(String impactpeople) {
        this.impactpeople = impactpeople;
    }

    public String getProjectShortDescription() {
        return projectShortDescription;
    }

    public void setProjectShortDescription(String projectShortDescription) {
        this.projectShortDescription = projectShortDescription;
    }

    public String getProjectDEpartmentName() {
        return projectDEpartmentName;
    }

    public void setProjectDEpartmentName(String projectDEpartmentName) {
        this.projectDEpartmentName = projectDEpartmentName;
    }

    public List<String> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(List<String> projectImages) {
        this.projectImages = projectImages;
    }

    public int getcompanieId(){
        return companieId;
    }
    public void setcompanieId(int companieId){
        this.companieId = companieId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getTotalProjectCost() {
        return totalProjectCost;
    }

    public void setTotalProjectCost(Double totalProjectCost) {
        this.totalProjectCost = totalProjectCost;
    }

    public Double getCsrFundingAmount() {
        return csrFundingAmount;
    }

    public void setCsrFundingAmount(Double csrFundingAmount) {
        this.csrFundingAmount = csrFundingAmount;
    }

    public LocalDate getMouSignedDate() {
        return mouSignedDate;
    }

    public void setMouSignedDate(LocalDate mouSignedDate) {
        this.mouSignedDate = mouSignedDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getFinancialProgress() {
        return financialProgress;
    }

    public void setFinancialProgress(String financialProgress) {
        this.financialProgress = financialProgress;
    }

    public String getPhysicalProgress() {
        return physicalProgress;
    }

    public void setPhysicalProgress(String physicalProgress) {
        this.physicalProgress = physicalProgress;
    }
}
