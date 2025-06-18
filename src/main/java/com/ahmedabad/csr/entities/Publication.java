package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "publication")
public class Publication {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publicationid;
    private String publicationname; 
    private String publicationshortdesc;
    private String publicationimage;
    private String publicationlink;
    public String getPublicationlink() {
        return publicationlink;
    }
    public void setPublicationlink(String publicationlink) {
        this.publicationlink = publicationlink;
    }
    public int getPublicationid() {
        return publicationid;
    }
    public void setPublicationid(int publicationid) {
        this.publicationid = publicationid;
    }
    public String getPublicationname() {
        return publicationname;
    }
    public void setPublicationname(String publicationname) {
        this.publicationname = publicationname;
    }
    public String getPublicationshortdesc() {
        return publicationshortdesc;
    }
    public void setPublicationshortdesc(String publicationshortdesc) {
        this.publicationshortdesc = publicationshortdesc;
    }
    public String getPublicationimage() {
        return publicationimage;
    }
    public void setPublicationimage(String publicationimage) {
        this.publicationimage = publicationimage;
    }
    public Publication(){} 
    public Publication(int publicationid, String publicationname, String publicationshortdesc, String publicationimage,
            String publicationlink) {
        this.publicationid = publicationid;
        this.publicationname = publicationname;
        this.publicationshortdesc = publicationshortdesc;
        this.publicationimage = publicationimage;
        this.publicationlink = publicationlink;
    }
    @Override
    public String toString() {
        return "Publication [publicationid=" + publicationid + ", publicationname=" + publicationname
                + ", publicationshortdesc=" + publicationshortdesc + ", publicationimage=" + publicationimage
                + ", publicationlink=" + publicationlink + "]";
    }
  
   

}
