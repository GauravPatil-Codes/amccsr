package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.FundAnIdea;
import com.ahmedabad.csr.repository.FundAnIdeaRepository;

@Service
public class FundAnIdeaServicesImpl implements FundAnIdeaServices {

    @Autowired
    private FundAnIdeaRepository fundAnIdeaRepository;

    // @Override
    // public FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea) {
    //     return fundAnIdeaRepository.save(fundAnIdea);
    // }

    @Override
    public List<FundAnIdea> getAllFundAnIdea() {
        return fundAnIdeaRepository.findAll();
    }

    @Override
    public Optional<FundAnIdea> getFundAnIdeaById(int fundanideaid) {
        return fundAnIdeaRepository.findById(fundanideaid);
    }

    @Override
    public FundAnIdea updateFundAnIdea(int fundanideaid, FundAnIdea fundAnIdea) {
        return fundAnIdeaRepository.findById(fundanideaid).map(existing -> {
            existing.setNatureofproject(fundAnIdea.getNatureofproject());
            existing.setFundanideaprojectname(fundAnIdea.getFundanideaprojectname());
            existing.setFundanideaprojectlocation(fundAnIdea.getFundanideaprojectlocation());
            existing.setFundanideadepartment(fundAnIdea.getFundanideadepartment());
            existing.setFundanideadocement(fundAnIdea.getFundanideadocement());
            existing.setFundanideadescription(fundAnIdea.getFundanideadescription());
            existing.setFundanideaorganizationname(fundAnIdea.getFundanideaorganizationname());
            existing.setFundanideaemailid(fundAnIdea.getFundanideaemailid());
            existing.setFundanideaphonenumber(fundAnIdea.getFundanideaphonenumber());
            existing.setFundanideacontactpersonname(fundAnIdea.getFundanideacontactpersonname());
            existing.setFundanideaestimateamount(fundAnIdea.getFundanideaestimateamount());
            existing.setFundanideastatus(fundAnIdea.getFundanideastatus());

            return fundAnIdeaRepository.save(existing);
        }).orElse(null);
    }

    @Override
    public void deleteFundAnIdea(int fundanideaid) {
    fundAnIdeaRepository.deleteById(fundanideaid);
    }

    @Override
public FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea) {
    String token = generateUniqueToken();
    fundAnIdea.setFundanideatoken(token);
    return fundAnIdeaRepository.save(fundAnIdea);
}

  private String generateUniqueToken() {
    String token;
    do {
        // Generate 10 random digits
        String randomPart = String.format("%010d", (long)(Math.random() * 1_000_000_0000L));
        token = "FA" + randomPart;
    } while (fundAnIdeaRepository.existsByFundanideatoken(token));
    return token;
}


   @Override
   public boolean existsByFundanideatoken(String fundanideatoken) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'existsByFundanideatoken'");
   }


}
