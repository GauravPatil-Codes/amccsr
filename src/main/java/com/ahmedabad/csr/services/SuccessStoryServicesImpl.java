package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.entities.SuccessStory;
import com.ahmedabad.csr.repository.SuccessStoryRepository;

@Service
public class SuccessStoryServicesImpl implements SuccessStoryServices {

    @Autowired
    private SuccessStoryRepository successStoryRepository;

    @Override
    public SuccessStory saveSuccessStory(SuccessStory successStory) {
        return successStoryRepository.save(successStory);
    }

    // read
    @Override
    public List<SuccessStory> getAllSuccessStory() {
        return (List<SuccessStory>) successStoryRepository.findAll();
    }

    // showbyid
    @Override
    public SuccessStory getSuccessStoryById(int successstoryId) {
        return successStoryRepository.findById(successstoryId).orElse(null);
    }

    // update
    @Override
    public SuccessStory updateSuccessStory(int successstoryId, SuccessStory updatedSuccessStory) {
        SuccessStory existingStory = getSuccessStoryById(successstoryId);

        if (updatedSuccessStory.getSuccessstoryTitle() != null) {
            existingStory.setSuccessstoryTitle(updatedSuccessStory.getSuccessstoryTitle());
        }
        if (updatedSuccessStory.getSuccessstoryDescription() != null) {
            existingStory.setSuccessstoryDescription(updatedSuccessStory.getSuccessstoryDescription());
        }
        if (updatedSuccessStory.getSuccessstoryImage() != null) {
            existingStory.setSuccessstoryImage(updatedSuccessStory.getSuccessstoryImage());
        }
        if (updatedSuccessStory.getCategoryId() != 0) {
            existingStory.setCategoryId(updatedSuccessStory.getCategoryId());
        }
        if (updatedSuccessStory.getNgoId() != 0) {
            existingStory.setNgoId(updatedSuccessStory.getNgoId());
        }
        if (updatedSuccessStory.getSuccessstoryDate() != null) {
            existingStory.setSuccessstoryDate(updatedSuccessStory.getSuccessstoryDate());
        }

        return successStoryRepository.save(existingStory);
    }

    // delete
    @Override
    public void deleteSuccessStoryById(int successstoryId) {
        SuccessStory successStory = successStoryRepository.findById(successstoryId)
                .orElseThrow(() -> new RuntimeException("Latest Update not found"));
        successStoryRepository.delete(successStory);
    }

    @Override
    public Page<SuccessStory> getSuccessStoryByCategoryId(int categoryId, Pageable pageable) {
        return successStoryRepository.findByCategoryId(categoryId, pageable);
    }
}
