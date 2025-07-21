package com.ahmedabad.csr.entities;

import jakarta.persistence.Column;
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
     private String authcomprepresentativename;
	  @Column(unique = true, nullable = false)
     private String authcomprepresentativeemail;
     private String companyname;
     private String companyurl;
     private int categoryId;
     private String status;
	 private String password;
	 public String getPassword() {
		return password;
	}
	 public void setPassword(String password) {
		 this.password = password;
	 }
	 public String getRole() {
		 return role;
	 }
	 public void setRole(String role) {
		 this.role = role;
	 }
	 private String role;
	public int getCompanieId() {
		return companieId;
	}
	public void setCompanieId(int companieId) {
		this.companieId = companieId;
	}
	public String getAuthcomprepresentativename() {
		return authcomprepresentativename;
	}
	public void setAuthcomprepresentativename(String authcomprepresentativename) {
		this.authcomprepresentativename = authcomprepresentativename;
	}
	public String getAuthcomprepresentativeemail() {
		return authcomprepresentativeemail;
	}
	public void setAuthcomprepresentativeemail(String authcomprepresentativeemail) {
		this.authcomprepresentativeemail = authcomprepresentativeemail;
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
	public Companies(int companieId, String authcomprepresentativename, String authcomprepresentativeemail,
			String companyname, String companyurl, int categoryId, String status, String password, String role) {
		super();
		this.companieId = companieId;
		this.authcomprepresentativename = authcomprepresentativename;
		this.authcomprepresentativeemail = authcomprepresentativeemail;
		this.companyname = companyname;
		this.companyurl = companyurl;
		this.categoryId = categoryId;
		this.status = status;
		this.password=password;
		this.role=role;
	}
	public Companies() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Companies [companieId=" + companieId + ", authcomprepresentativename=" + authcomprepresentativename
				+ ", authcomprepresentativeemail=" + authcomprepresentativeemail + ", companyname=" + companyname
				+ ", companyurl=" + companyurl + ", categoryId=" + categoryId + ", status=" + status + ", password="
				+ password + ", role=" + role + "]";
	}
     
     
   
}
