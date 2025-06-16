package com.ahmedabad.csr.entities;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String ngoId;
    private String categoryId;
    private String projectMainImage;
    private String projectBudget;
    private String projectLocation;
    private String impactpeople;
    private String projectShortDescription;

    private String projectDEpartmentName;
    @ElementCollection
    private List<String> projectImages;

    
    // default constructor
    public Project() {

    }

    @Override
    public String toString() {
        return "Project [projetcId=" + projetcId + ", projetcName=" + projetcName + ", projetcDescription="
                + projetcDescription + ", projectStatus=" + projectStatus + ", ngoId=" + ngoId + ", categoryId="
                + categoryId + ", projectMainImage=" + projectMainImage + ", projectBudget=" + projectBudget
                + ", projectLocation=" + projectLocation + ", impactpeople=" + impactpeople
                + ", projectShortDescription=" + projectShortDescription + ", projectDEpartmentName="
                + projectDEpartmentName + ", projectImages=" + projectImages + "]";
    }

    public int getProjectId() {
        return projetcId;
    }

    public void setProjectId(int projetcId) {
        this.projetcId = projetcId;
    }

    public Project(int projetcId, String projetcName, String projetcDescription, String projectStatus, String ngoId,
            String categoryId, String projectMainImage, String projectBudget, String projectLocation,
            String impactpeople, String projectShortDescription, String projectDEpartmentName,
            List<String> projectImages) {
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

    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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

}
