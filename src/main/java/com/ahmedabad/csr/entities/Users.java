package com.ahmedabad.csr.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private String role;
    private String name;
    private String email;
    private String password;
    private String organizationname;
    private String departmentname;
    private String phonenumber;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getOrganizationname() {
        return organizationname;
    }
    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }
    public String getDepartmentname() {
        return departmentname;
    }
    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public Users(){}
    public Users(Long id, String role, String name, String email, String password, String organizationname,
            String departmentname, String phonenumber) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.organizationname = organizationname;
        this.departmentname = departmentname;
        this.phonenumber = phonenumber;
    }
    @Override
    public String toString() {
        return "Users [id=" + id + ", role=" + role + ", name=" + name + ", email=" + email + ", password=" + password
                + ", organizationname=" + organizationname + ", departmentname=" + departmentname + ", phonenumber="
                + phonenumber + "]";
    }

}