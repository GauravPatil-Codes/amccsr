package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Companies;
import com.ahmedabad.csr.repository.CompaniesRepository;

@Service
public class CompaniesServicesImpl implements CompaninesServices {

    
    @Autowired
    private CompaniesRepository companiesRepository;

    @Override
    public Companies addCompany(Companies company) {
        // Add validation if needed
        if (company.getCompanyname() == null || company.getCompanyname().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        return companiesRepository.save(company);
    }

    @Override
    public Optional<Companies> getCompanyById(int companyId) {
        return companiesRepository.findById(companyId);
    }

    @Override
    public Companies updateCompany(int companyId, Companies companyDetails) {
        Companies company = companiesRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));
        
        // Update fields
        if (companyDetails.getCompanyname() != null) {
            company.setCompanyname(companyDetails.getCompanyname());
        }
        if (companyDetails.getCompanyurl() != null) {
            company.setCompanyurl(companyDetails.getCompanyurl());
        }
        if (companyDetails.getCategoryId() > 0) {
            company.setCategoryId(companyDetails.getCategoryId());
        }
        if (companyDetails.getStatus() != null) {
            company.setStatus(companyDetails.getStatus());
        }
        
        return companiesRepository.save(company);
    }

    @Override
    public void deleteCompany(int companyId) {
        if (!companiesRepository.existsById(companyId)) {
            throw new RuntimeException("Company not found with id: " + companyId);
        }
        companiesRepository.deleteById(companyId);
    }

    @Override
    public Page<Companies> getAllCompanies(Pageable pageable) {
        return companiesRepository.findAll(pageable);
    }

    @Override
    public Page<Companies> getCompaniesByCategory(int categoryId, Pageable pageable) {
        return companiesRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Companies> filterCompanies(String companyName, Integer categoryId, String status, Pageable pageable) {
        if (companyName != null && categoryId != null && status != null) {
            return companiesRepository.findByCompanynameContainingAndCategoryIdAndStatus(
                    companyName, categoryId, status, pageable);
        } else if (companyName != null && categoryId != null) {
            return companiesRepository.findByCompanynameContainingAndCategoryId(
                    companyName, categoryId, pageable);
        } else if (companyName != null && status != null) {
            return companiesRepository.findByCompanynameContainingAndStatus(
                    companyName, status, pageable);
        } else if (categoryId != null && status != null) {
            return companiesRepository.findByCategoryIdAndStatus(
                    categoryId, status, pageable);
        } else if (companyName != null) {
            return companiesRepository.findByCompanynameContaining(companyName, pageable);
        } else if (categoryId != null) {
            return companiesRepository.findByCategoryId(categoryId, pageable);
        } else if (status != null) {
            return companiesRepository.findByStatus(status, pageable);
        } else {
            return companiesRepository.findAll(pageable);
        }
    }
}