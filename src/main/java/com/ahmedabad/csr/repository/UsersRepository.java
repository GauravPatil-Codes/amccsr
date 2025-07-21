package com.ahmedabad.csr.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ahmedabad.csr.entities.Users;
public interface UsersRepository extends JpaRepository<Users, Long> {
    
    Optional<Users> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    Optional<Users> findByEmailAndPassword(String email, String password);
}