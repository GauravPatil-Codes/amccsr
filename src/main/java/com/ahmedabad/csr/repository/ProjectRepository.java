package com.ahmedabad.csr.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.Project;

public interface ProjectRepository extends JpaRepository<Project , Integer>{       

}
