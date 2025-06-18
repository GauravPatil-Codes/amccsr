package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String documenttitle;
    private String documentType;
    private String documenturl;
    private String documentshortdesc;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDocumenttitle() {
        return documenttitle;
    }
    public void setDocumenttitle(String documenttitle) {
        this.documenttitle = documenttitle;
    }
    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public String getDocumenturl() {
        return documenturl;
    }
    public void setDocumenturl(String documenturl) {
        this.documenturl = documenturl;
    }
    public String getDocumentshortdesc() {
        return documentshortdesc;
    }
    public void setDocumentshortdesc(String documentshortdesc) {
        this.documentshortdesc = documentshortdesc;
    }
     public Documents(){}
    public Documents(int id, String documenttitle, String documentType, String documenturl, String documentshortdesc) {
        this.id = id;
        this.documenttitle = documenttitle;
        this.documentType = documentType;
        this.documenturl = documenturl;
        this.documentshortdesc = documentshortdesc;
    }
    @Override
    public String toString() {
        return "Documents [id=" + id + ", documenttitle=" + documenttitle + ", documentType=" + documentType
                + ", documenturl=" + documenturl + ", documentshortdesc=" + documentshortdesc + "]";
    }
    
}
