package com.ahmedabad.csr.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "gallery")
public class Gallery {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String fileUrl;
    private String fileType; // "image" or "video"
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Gallery() {}
    
    public Gallery(int id, String fileUrl, String fileType, LocalDateTime createdAt) {
        this.id = id;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Gallery [id=" + id + ", fileUrl=" + fileUrl + ", fileType=" + fileType + ", createdAt=" + createdAt
                + "]";
    }
    
}
