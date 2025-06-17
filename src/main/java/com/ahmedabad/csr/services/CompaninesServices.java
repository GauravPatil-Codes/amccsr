package com.ahmedabad.csr.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ahmedabad.csr.entities.Companies;

public interface CompaninesServices {
    Companies addCompany(Companies company);
    Optional<Companies> getCompanyById(int companyId);
    Companies updateCompany(int companyId, Companies companyDetails);
    void deleteCompany(int companyId);
    Page<Companies> getAllCompanies(Pageable pageable);
    Page<Companies> getCompaniesByCategory(int categoryId, Pageable pageable);
    Page<Companies> filterCompanies(String companyName, Integer categoryId, String status, Pageable pageable);
}