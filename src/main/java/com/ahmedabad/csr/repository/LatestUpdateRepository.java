package com.ahmedabad.csr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.LatestUpdate;

public interface LatestUpdateRepository extends JpaRepository<LatestUpdate, Integer> {

    Optional<LatestUpdate> findBystatus(String status);

 }
