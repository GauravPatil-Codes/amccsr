package com.ahmedabad.csr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.SuccessStory;


public interface SuccessStoryRepository extends JpaRepository< SuccessStory ,Integer> {
Page<SuccessStory> findByCategoryId(int categoryId, Pageable pageable);

static Page<SuccessStory> getSuccesStoryByCategoryId(int categoryId, Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getSuccesStoryByCategoryId'");
}
}
