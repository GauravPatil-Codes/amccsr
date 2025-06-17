package com.ahmedabad.csr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.SuccessStory;
import com.ahmedabad.csr.repository.SuccessStoryRepository;

@Service
public class SuccessStoryServicesImpl implements SuccessStoryServices {

    @Autowired
    private SuccessStoryRepository successStoryRepository;

    @Override
    public SuccessStory saveSuccessStory(SuccessStory successStory){
      return  successStoryRepository.save(successStory);
    }
}
