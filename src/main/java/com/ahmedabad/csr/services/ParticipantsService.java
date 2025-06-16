package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import com.ahmedabad.csr.entities.Participants;

public interface ParticipantsService {
  Participants saveParticipant(Participants participant);
    List<Participants> getAllParticipants();
    Optional<Participants> getParticipantById(int id);
    Participants updateParticipant(int id, Participants participant);
    void deleteParticipant(int id);
}
