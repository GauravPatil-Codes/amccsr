// Updated UsersServiceImpl with standardized response format
package com.ahmedabad.csr.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Users;
import com.ahmedabad.csr.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
    
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Map<String, Object> registerUser(Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Extract user data
            String name = userData.get("name");
            String email = userData.get("email");
            String password = userData.get("password");
            String role = userData.getOrDefault("role", "USER");
            String organizationname = userData.get("organizationname");
            String departmentname = userData.get("departmentname");
            String phonenumber = userData.get("phonenumber");
            
            // Validate required fields
            if (email == null || email.trim().isEmpty()) {
                response.put("status", 400);
                response.put("message", "Email is required");
                response.put("data", null);
                return response;
            }
            
            if (password == null || password.trim().isEmpty()) {
                response.put("status", 400);
                response.put("message", "Password is required");
                response.put("data", null);
                return response;
            }
            
            if (name == null || name.trim().isEmpty()) {
                response.put("status", 400);
                response.put("message", "Name is required");
                response.put("data", null);
                return response;
            }
            
            // Check if email already exists
            if (usersRepository.existsByEmail(email.trim().toLowerCase())) {
                response.put("status", 400);
                response.put("message", "Email already registered");
                response.put("data", null);
                return response;
            }
            
            // Create new user
            Users newUser = new Users();
            newUser.setName(name.trim());
            newUser.setEmail(email.trim().toLowerCase());
            newUser.setPassword(password); // In production, hash this password
            newUser.setRole(role.toUpperCase());
            newUser.setOrganizationname(organizationname != null ? organizationname.trim() : null);
            newUser.setDepartmentname(departmentname != null ? departmentname.trim() : null);
            newUser.setPhonenumber(phonenumber != null ? phonenumber.trim() : null);
            
            // Save user
            Users savedUser = usersRepository.save(newUser);
            
            // Prepare user data for response
            Map<String, Object> userData_response = new HashMap<>();
            userData_response.put("userId", savedUser.getId());
            userData_response.put("name", savedUser.getName());
            userData_response.put("email", savedUser.getEmail());
            userData_response.put("role", savedUser.getRole());
            userData_response.put("organizationname", savedUser.getOrganizationname());
            userData_response.put("departmentname", savedUser.getDepartmentname());
            userData_response.put("phonenumber", savedUser.getPhonenumber());
            
            // Return standardized success response
            response.put("status", 200);
            response.put("message", "User registered successfully");
            response.put("data", userData_response);
            
            logger.info("✅ User registered successfully: {} with ID: {}", email, savedUser.getId());
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error registering user: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Registration failed: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    @Override
    public Map<String, Object> loginUser(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate input
            if (email == null || email.trim().isEmpty()) {
                response.put("status", 400);
                response.put("message", "Email is required");
                response.put("data", null);
                return response;
            }
            
            if (password == null || password.trim().isEmpty()) {
                response.put("status", 400);
                response.put("message", "Password is required");
                response.put("data", null);
                return response;
            }
            
            // Find user by email and password
            Optional<Users> userOpt = usersRepository.findByEmailAndPassword(email.trim().toLowerCase(), password);
            
            if (userOpt.isPresent()) {
                Users user = userOpt.get();
                
                // Prepare user data for response
                Map<String, Object> userData_response = new HashMap<>();
                userData_response.put("userId", user.getId());
                userData_response.put("name", user.getName());
                userData_response.put("email", user.getEmail());
                userData_response.put("role", user.getRole());
                userData_response.put("organizationname", user.getOrganizationname());
                userData_response.put("departmentname", user.getDepartmentname());
                userData_response.put("phonenumber", user.getPhonenumber());
                
                // Login successful
                response.put("status", 200);
                response.put("message", "Login successful");
                response.put("data", userData_response);
                
                logger.info("✅ User logged in successfully: {} (ID: {})", email, user.getId());
                return response;
                
            } else {
                // Login failed
                response.put("status", 401);
                response.put("message", "Invalid email or password");
                response.put("data", null);
                
                logger.warn("❌ Failed login attempt for email: {}", email);
                return response;
            }
            
        } catch (Exception e) {
            logger.error("❌ Error during login: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Login failed: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        try {
            return usersRepository.findByEmail(email.trim().toLowerCase());
        } catch (Exception e) {
            logger.error("❌ Error finding user by email: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Users> findById(Long id) {
        try {
            return usersRepository.findById(id);
        } catch (Exception e) {
            logger.error("❌ Error finding user by ID: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<Users> getAllUsers() {
        try {
            return usersRepository.findAll();
        } catch (Exception e) {
            logger.error("❌ Error getting all users: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public boolean emailExists(String email) {
        try {
            return usersRepository.existsByEmail(email.trim().toLowerCase());
        } catch (Exception e) {
            logger.error("❌ Error checking if email exists: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Map<String, Object> updateUser(Long id, Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Users> userOpt = usersRepository.findById(id);
            
            if (userOpt.isEmpty()) {
                response.put("status", 404);
                response.put("message", "User not found");
                response.put("data", null);
                return response;
            }
            
            Users user = userOpt.get();
            
            // Update fields if provided
            if (userData.get("name") != null && !userData.get("name").trim().isEmpty()) {
                user.setName(userData.get("name").trim());
            }
            
            if (userData.get("role") != null && !userData.get("role").trim().isEmpty()) {
                user.setRole(userData.get("role").toUpperCase());
            }
            
            if (userData.get("organizationname") != null) {
                user.setOrganizationname(userData.get("organizationname").trim());
            }
            
            if (userData.get("departmentname") != null) {
                user.setDepartmentname(userData.get("departmentname").trim());
            }
            
            if (userData.get("phonenumber") != null) {
                user.setPhonenumber(userData.get("phonenumber").trim());
            }
            
            // Save updated user
            Users updatedUser = usersRepository.save(user);
            
            // Prepare user data for response
            Map<String, Object> userData_response = new HashMap<>();
            userData_response.put("userId", updatedUser.getId());
            userData_response.put("name", updatedUser.getName());
            userData_response.put("email", updatedUser.getEmail());
            userData_response.put("role", updatedUser.getRole());
            userData_response.put("organizationname", updatedUser.getOrganizationname());
            userData_response.put("departmentname", updatedUser.getDepartmentname());
            userData_response.put("phonenumber", updatedUser.getPhonenumber());
            
            response.put("status", 200);
            response.put("message", "User updated successfully");
            response.put("data", userData_response);
            
            logger.info("✅ User updated successfully: {}", updatedUser.getEmail());
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error updating user: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Update failed: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    @Override
    public Map<String, Object> deleteUser(Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Users> userOpt = usersRepository.findById(id);
            
            if (userOpt.isEmpty()) {
                response.put("status", 404);
                response.put("message", "User not found");
                response.put("data", null);
                return response;
            }
            
            Users user = userOpt.get();
            usersRepository.deleteById(id);
            
            Map<String, Object> deletedUserData = new HashMap<>();
            deletedUserData.put("deletedUserId", id);
            deletedUserData.put("deletedUserEmail", user.getEmail());
            deletedUserData.put("deletedUserName", user.getName());
            
            response.put("status", 200);
            response.put("message", "User deleted successfully");
            response.put("data", deletedUserData);
            
            logger.info("✅ User deleted successfully: {} (ID: {})", user.getEmail(), id);
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error deleting user: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Delete failed: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    // Helper method to get all users with standardized response
    public Map<String, Object> getAllUsersResponse() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Users> users = usersRepository.findAll();
            
            response.put("status", 200);
            response.put("message", "Users fetched successfully");
            response.put("data", users);
            
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error getting all users: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Failed to fetch users: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    // Helper method to check email with standardized response
    public Map<String, Object> checkEmailResponse(String email) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean exists = usersRepository.existsByEmail(email.trim().toLowerCase());
            
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("email", email);
            emailData.put("exists", exists);
            
            response.put("status", 200);
            response.put("message", exists ? "Email already exists" : "Email is available");
            response.put("data", emailData);
            
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error checking email: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Failed to check email: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    // Helper method to get user by email with standardized response
    public Map<String, Object> getUserByEmailResponse(String email) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Users> userOpt = findByEmail(email);
            
            if (userOpt.isPresent()) {
                Users user = userOpt.get();
                
                Map<String, Object> userData_response = new HashMap<>();
                userData_response.put("userId", user.getId());
                userData_response.put("name", user.getName());
                userData_response.put("email", user.getEmail());
                userData_response.put("role", user.getRole());
                userData_response.put("organizationname", user.getOrganizationname());
                userData_response.put("departmentname", user.getDepartmentname());
                userData_response.put("phonenumber", user.getPhonenumber());
                
                response.put("status", 200);
                response.put("message", "User found successfully");
                response.put("data", userData_response);
            } else {
                response.put("status", 404);
                response.put("message", "User not found");
                response.put("data", null);
            }
            
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error getting user by email: {}", e.getMessage(), e);
            response.put("status", 500);
            response.put("message", "Failed to get user: " + e.getMessage());
            response.put("data", null);
            return response;
        }
    }

    @Override
    public Optional<Users> getUserByEmailAndPassword(String email, String password) {
        return usersRepository.findByEmail(email)
            .filter(u -> u.getPassword() != null && u.getPassword().equals(password));
    }
}