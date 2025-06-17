package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="successstory")
public class SuccessStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int successstoryId;
    private String successstoryTitle;
    private String successstoryDescription;
    private String successstoryImage;
    private int categoryId;
    private int ngoId;
    private String successstoryDate;
    public int getSuccessstoryId() {
        return successstoryId;
    }
    public void setSuccessstoryId(int successstoryId) {
        this.successstoryId = successstoryId;
    }
    public String getSuccessstoryTitle() {
        return successstoryTitle;
    }
    public void setSuccessstoryTitle(String successstoryTitle) {
        this.successstoryTitle = successstoryTitle;
    }
    public String getSuccessstoryDescription() {
        return successstoryDescription;
    }
    public void setSuccessstoryDescription(String successstoryDescription) {
        this.successstoryDescription = successstoryDescription;
    }
    public String getSuccessstoryImage() {
        return successstoryImage;
    }
    public void setSuccessstoryImage(String successstoryImage) {
        this.successstoryImage = successstoryImage;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getNgoId() {
        return ngoId;
    }
    public void setNgoId(int ngoId) {
        this.ngoId = ngoId;
    }
    public String getSuccessstoryDate() {
        return successstoryDate;
    }
    public void setSuccessstoryDate(String successstoryDate) {
        this.successstoryDate = successstoryDate;
    }
    
    public SuccessStory(){}

    public SuccessStory(int successstoryId, String successstoryTitle, String successstoryDescription,
            String successstoryImage, int categoryId, int ngoId, String successstoryDate) {
        this.successstoryId = successstoryId;
        this.successstoryTitle = successstoryTitle;
        this.successstoryDescription = successstoryDescription;
        this.successstoryImage = successstoryImage;
        this.categoryId = categoryId;
        this.ngoId = ngoId;
        this.successstoryDate = successstoryDate;
    }
    @Override
    public String toString() {
        return "SuccessStory [successstoryId=" + successstoryId + ", successstoryTitle=" + successstoryTitle
                + ", successstoryDescription=" + successstoryDescription + ", successstoryImage=" + successstoryImage
                + ", categoryId=" + categoryId + ", ngoId=" + ngoId + ", successstoryDate=" + successstoryDate + "]";
    }
    
}
