package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Participants;
import com.ahmedabad.csr.repository.ParticipantsRepository;

@Service
public class ParticipantsServiceImpl implements ParticipantsService {

    @Autowired
    private ParticipantsRepository participantsRepository;

    @Override
    public Participants saveParticipant(Participants participant) {
        return participantsRepository.save(participant);
    }

    @Override
    public List<Participants> getAllParticipants() {
        return participantsRepository.findAll();
    }

    @Override
    public Optional<Participants> getParticipantById(int id) {
        return participantsRepository.findById(id);
    }

    @Override
    public Participants updateParticipant(int id, Participants participant) {
        return participantsRepository.findById(id).map(existing -> {
            existing.setParticipantName(participant.getParticipantName());
            existing.setOrganizationName(participant.getOrganizationName());
            existing.setParticipantEmail(participant.getParticipantEmail());
            existing.setParticipantMobileNumber(participant.getParticipantMobileNumber());
            existing.setAmount(participant.getAmount());
            return participantsRepository.save(existing);
        }).orElse(null);
    }

    @Override
    public void deleteParticipant(int id) {
        participantsRepository.deleteById(id);
    }
}
