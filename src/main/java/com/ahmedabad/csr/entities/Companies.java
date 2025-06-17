package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Companies {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int companieId;
     private String companyname;
     private String companyurl;
     private int categoryId;
     private String status;
     public int getCompanieId() {
         return companieId;
     }
     public void setCompanieId(int companieId) {
         this.companieId = companieId;
     }
     public String getCompanyname() {
         return companyname;
     }
     public void setCompanyname(String companyname) {
         this.companyname = companyname;
     }
     public String getCompanyurl() {
         return companyurl;
     }
     public void setCompanyurl(String companyurl) {
         this.companyurl = companyurl;
     }
     public int getCategoryId() {
         return categoryId;
     }
     public void setCategoryId(int categoryId) {
         this.categoryId = categoryId;
     }
     public String getStatus() {
         return status;
     }
     public void setStatus(String status) {
         this.status = status;
     }
      public Companies(){}
     public Companies(int companieId, String companyname, String companyurl, int categoryId, String status) {
        this.companieId = companieId;
        this.companyname = companyname;
        this.companyurl = companyurl;
        this.categoryId = categoryId;
        this.status = status;
     }
     @Override
     public String toString() {
        return "Companies [companieId=" + companieId + ", companyname=" + companyname + ", companyurl=" + companyurl
                + ", categoryId=" + categoryId + ", status=" + status + "]";
     }

}
