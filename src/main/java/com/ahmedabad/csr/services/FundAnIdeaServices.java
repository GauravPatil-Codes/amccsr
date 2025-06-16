package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import com.ahmedabad.csr.entities.FundAnIdea;

public interface FundAnIdeaServices {
    FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea);

    List<FundAnIdea> getAllFundAnIdea();

    Optional<FundAnIdea> getFundAnIdeaById(int fundanideaid);

    FundAnIdea updateFundAnIdea(int fundanideaid, FundAnIdea fundAnIdea);

    void deleteFundAnIdea(int fundanideaid);
}
