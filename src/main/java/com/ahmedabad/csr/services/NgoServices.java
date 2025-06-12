package com.ahmedabad.csr.services;

import java.util.List;

import com.ahmedabad.csr.entities.NGO;


public interface NgoServices {
    
    NGO saveNGO(NGO ngo);
     boolean isEmailExists(String emailId);
    boolean isUserNameExists(String username);
     List<NGO> getAllNGO() ;
}