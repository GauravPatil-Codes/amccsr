package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ahmedabad.csr.entities.SuccessStory;

public interface SuccessStoryServices {
    SuccessStory saveSuccessStory(SuccessStory successStory);

    List<SuccessStory> getAllSuccessStory();

    SuccessStory getSuccessStoryById(int successstoryId);

    SuccessStory updateSuccessStory(int successstoryId, SuccessStory updatedSuccessStory);

    void deleteSuccessStoryById(int successstoryId);

    Page<SuccessStory> getSuccessStoryByCategoryId(int categoryId, Pageable pageable);
}
