package com.ahmedabad.csr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ahmedabad.csr.entities.Participants;

public interface ParticipantsRepository extends JpaRepository<Participants, Integer> {
    boolean existsByToken(String token);

    Optional<Participants> findByToken(String token);

}
