package com.ahmedabad.csr.services;

import com.ahmedabad.csr.entities.Publication;
import com.ahmedabad.csr.repository.PublicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository repository;

    @Override
    public Publication save(Publication publication) {
        return repository.save(publication);
    }

    @Override
    public Optional<Publication> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Page<Publication> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public Publication update(int id, Publication updated) {
        return repository.findById(id).map(existing -> {
            existing.setPublicationname(updated.getPublicationname());
            existing.setPublicationshortdesc(updated.getPublicationshortdesc());
            existing.setPublicationimage(updated.getPublicationimage());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Publication not found with ID: " + id));
    }
}
