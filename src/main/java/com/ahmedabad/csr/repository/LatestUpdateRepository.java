package com.ahmedabad.csr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.LatestUpdate;

public interface LatestUpdateRepository extends JpaRepository<LatestUpdate, Integer> { }
