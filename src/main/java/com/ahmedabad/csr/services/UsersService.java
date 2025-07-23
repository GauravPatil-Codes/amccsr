package com.ahmedabad.csr.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.ahmedabad.csr.entities.Users;
public interface UsersService {
 Map<String, Object> registerUser(Map<String, String> userData);
    
    Map<String, Object> loginUser(String email, String password);
    
    Optional<Users> findByEmail(String email);
    Optional<Users>getUserByEmailAndPassword(String email,String password);
    
    List<Users> getAllUsers();
    
    boolean emailExists(String email);
     Optional<Users> findById(Long id);
     Map<String, Object> deleteUser(Long id);
   Users getUserById(long id);
    Users updateUser(long id, Users updatedUser) ;
}
