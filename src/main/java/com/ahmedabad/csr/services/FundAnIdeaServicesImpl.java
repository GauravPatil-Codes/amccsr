package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.FundAnIdea;
import com.ahmedabad.csr.entities.Participants;
import com.ahmedabad.csr.repository.FundAnIdeaRepository;

@Service
public class FundAnIdeaServicesImpl implements FundAnIdeaServices {

    @Autowired
    private FundAnIdeaRepository fundAnIdeaRepository;

   @Override
    public FundAnIdea saveFundAnIdea(FundAnIdea fundAnIdea) {
        return fundAnIdeaRepository.save(fundAnIdea);
    }

    @Override
    public List<FundAnIdea> getAllFundAnIdea() {
        return fundAnIdeaRepository.findAll();
    }

    @Override
    public Optional<FundAnIdea> getFundAnIdeaById(int fundanideaid) {
        return fundAnIdeaRepository.findById(fundanideaid);
    }

//     @Override
//     public FundAnIdea updateFundAnIdea(int id, FundAnIdea fundAnIdea) {
//         return fundAnIdeaRepository.findById(id).map(existing -> {
//             existing.setParticipantName(participant.getParticipantName());
//             existing.setOrganizationName(participant.getOrganizationName());
//             existing.setParticipantEmail(participant.getParticipantEmail());
//             existing.setParticipantMobileNumber(participant.getParticipantMobileNumber());
//             existing.setAmount(participant.getAmount());
//             return participantsRepository.save(existing);
//         }).orElse(null);
//     }

//     @Override
//     public void deleteParticipant(int id) {
//         participantsRepository.deleteById(id);
//     }   
}
