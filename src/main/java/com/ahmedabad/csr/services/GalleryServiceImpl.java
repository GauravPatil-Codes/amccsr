package com.ahmedabad.csr.services;

import com.ahmedabad.csr.entities.Companies;
import com.ahmedabad.csr.entities.Gallery;
import com.ahmedabad.csr.entities.LatestUpdate;
import com.ahmedabad.csr.repository.GalleryRepository;
import com.ahmedabad.csr.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Override
    public Gallery saveGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery updateGallery(int id, Gallery updatedGallery) {
        Gallery existing = galleryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Gallery not found with ID: " + id));

        existing.setFileUrl(updatedGallery.getFileUrl());
        existing.setFileType(updatedGallery.getFileType());

        return galleryRepository.save(existing);
    }

    @Override
    public void deleteGallery(int id) {
        galleryRepository.deleteById(id);
    }

    @Override
    public Gallery getGalleryById(int id) {
        return galleryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found with ID: " + id));
    }

    @Override
    public List<Gallery> getAllGallery() {
        return galleryRepository.findAll();
    }
 @Override
    public Page<Gallery> getgallerybyfiletype(String fileType, Pageable pageable) {
        return galleryRepository.findByFileType(fileType, pageable);
    }
   
}
