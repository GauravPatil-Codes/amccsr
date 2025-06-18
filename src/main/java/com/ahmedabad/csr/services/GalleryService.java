package com.ahmedabad.csr.services;

import com.ahmedabad.csr.entities.Gallery;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GalleryService {
    Gallery saveGallery(Gallery gallery);
    Gallery updateGallery(int id, Gallery updatedGallery);
    void deleteGallery(int id);
    Gallery getGalleryById(int id);
    List<Gallery> getAllGallery() ;
    Page<Gallery> getgallerybyfiletype(String fileType, Pageable pageable) ;
}
