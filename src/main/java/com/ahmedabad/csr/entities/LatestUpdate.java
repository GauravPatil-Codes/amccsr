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
  
    public int getLatestupdateid() {
        return letestupdateid;
    }
    public void setLatestupdateid(int letestupdateid) {
        this.letestupdateid = letestupdateid;
    }
    public String getLatestupdatetitle() {
        return letestupdatetitle;
    }
    public void setLatestupdatetitle(String letestupdatetitle) {
        this.letestupdatetitle = letestupdatetitle;
    }
    public String getLatestupdatedesc() {
        return letestupdatedesc;
    }
    public void setLatestupdatedesc(String letestupdatedesc) {
        this.letestupdatedesc = letestupdatedesc;
    }
    public String getLatestupdateimage() {
        return letestupdateimage;
    }

       public LatestUpdate() {
       
    }

    @Override
    public String toString() {
        return "LatestUpdate [letestupdateid=" + letestupdateid + ", letestupdatetitle=" + letestupdatetitle
                + ", letestupdatedesc=" + letestupdatedesc + ", letestupdateimage=" + letestupdateimage + "]";
    }
    public LatestUpdate(int letestupdateid, String letestupdatetitle, String letestupdatedesc,
            String letestupdateimage) {
        this.letestupdateid = letestupdateid;
        this.letestupdatetitle = letestupdatetitle;
        this.letestupdatedesc = letestupdatedesc;
        this.letestupdateimage = letestupdateimage;
    }
    public void setLatestupdateimage(String letestupdateimage) {
        this.letestupdateimage = letestupdateimage;
    }
}
