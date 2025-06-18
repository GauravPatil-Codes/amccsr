package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "letestupdate")
public class LatestUpdate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int letestupdateid;
    private String letestupdatetitle;
    private String letestupdatedesc;
    private String letestupdateimage;
    private String status;
    public int getLetestupdateid() {
        return letestupdateid;
    }
    public void setLetestupdateid(int letestupdateid) {
        this.letestupdateid = letestupdateid;
    }
    public String getLetestupdatetitle() {
        return letestupdatetitle;
    }
    public void setLetestupdatetitle(String letestupdatetitle) {
        this.letestupdatetitle = letestupdatetitle;
    }
    public String getLetestupdatedesc() {
        return letestupdatedesc;
    }
    public void setLetestupdatedesc(String letestupdatedesc) {
        this.letestupdatedesc = letestupdatedesc;
    }
    public String getLetestupdateimage() {
        return letestupdateimage;
    }
    public void setLetestupdateimage(String letestupdateimage) {
        this.letestupdateimage = letestupdateimage;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
     public LatestUpdate(){}
    public LatestUpdate(int letestupdateid, String letestupdatetitle, String letestupdatedesc, String letestupdateimage,
            String status) {
        this.letestupdateid = letestupdateid;
        this.letestupdatetitle = letestupdatetitle;
        this.letestupdatedesc = letestupdatedesc;
        this.letestupdateimage = letestupdateimage;
        this.status = status;
    }
    @Override
    public String toString() {
        return "LatestUpdate [letestupdateid=" + letestupdateid + ", letestupdatetitle=" + letestupdatetitle
                + ", letestupdatedesc=" + letestupdatedesc + ", letestupdateimage=" + letestupdateimage + ", status="
                + status + "]";
    }
    
}