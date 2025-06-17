package com.ahmedabad.csr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.FundAnIdea;

public interface FundAnIdeaRepository extends JpaRepository<FundAnIdea, Integer> {

    boolean existsByFundanideatoken(String fundanideatoken);
}
