package com.ahmedabad.csr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.NGO;
import com.ahmedabad.csr.repository.NgoRepository;

@Service
public class NgoServicesImpl implements NgoServices {

    private NgoRepository ngoRepository;
 @Autowired
    public NgoServicesImpl(NgoRepository ngoRepository) {
        this.ngoRepository = ngoRepository;
    }

    @Override
    public boolean isEmailExists(String emailId) {
        return ngoRepository.existsByEmailIdIgnoreCase(emailId);
    }

    @Override
    public boolean isUserNameExists(String username) {
        return ngoRepository.existsByUserNameIgnoreCase(username);
    }

    @Override
    public NGO saveNGO(NGO ngo) {
        // FIXED: Using correct getter methods
        if (isEmailExists(ngo.getEmailId())) {
            throw new RuntimeException("Email ID already exists: " + ngo.getEmailId());
        }
        if (isUserNameExists(ngo.getUserName())) {
            throw new RuntimeException("Username already exists: " + ngo.getUserName());
        }
        return ngoRepository.save(ngo);
    }
}