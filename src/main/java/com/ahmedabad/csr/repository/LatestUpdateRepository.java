package com.ahmedabad.csr.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.LatestUpdate;

public interface LatestUpdateRepository extends JpaRepository<LatestUpdate, Integer> {

    Page<LatestUpdate> findBystatus(String status, Pageable pageable);

    Page<LatestUpdate> findByStatus(String status, Pageable pageable);

 }
