// Updated AuthController with standardized response format
package com.ahmedabad.csr.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ahmedabad.csr.entities.Companies;
import com.ahmedabad.csr.entities.Users;
import com.ahmedabad.csr.services.CompaninesServices;
import com.ahmedabad.csr.services.UsersService;
import com.ahmedabad.csr.services.UsersServiceImpl;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersServiceImpl usersServiceImpl;

    @Autowired
    private CompaninesServices companinesServices;

    /**
     * Register new user
     * POST /registeruser
     */
    @PostMapping("/registeruser")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> userData) {
        logger.info("📝 Registration attempt for email: {}", userData.get("email"));

        Map<String, Object> response = usersService.registerUser(userData);

        // Return response with appropriate HTTP status
        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Map<String, Object> response = new HashMap<>();

        // --- Step 1: Try Users table ---
        Optional<Users> userOpt = usersService.getUserByEmailAndPassword(email, password);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            response.put("status", 200);
            response.put("message", "Login successful.");
            response.put("role", user.getRole());
            response.put("user", user);
            return ResponseEntity.ok(response);
        }

        // --- Step 2: Try Companies (corporate) ---
        Optional<Companies> companyOpt = companinesServices.getByRepresentativeEmail(email);
        if (companyOpt.isPresent()) {
            Companies company = companyOpt.get();

            // Require password for corporate login
            if (password == null || password.isBlank()) {
                response.put("status", 400);
                response.put("message", "Password is required for corporate login.");
                return ResponseEntity.status(400).body(response);
            }

            // Plaintext compare (since no encoder)
            String stored = company.getPassword(); // make sure Companies has this field + getter
            if (stored == null || !stored.equals(password)) {
                response.put("status", 401);
                response.put("message", "Invalid corporate credentials.");
                return ResponseEntity.status(401).body(response);
            }

            // Success — build safe company payload (no password!)
            Map<String, Object> corp = new HashMap<>();
            corp.put("companieId", company.getCompanieId());
            corp.put("authcomprepresentativename", company.getAuthcomprepresentativename());
            corp.put("authcomprepresentativeemail", company.getAuthcomprepresentativeemail());
            corp.put("companyname", company.getCompanyname());
            corp.put("companyurl", company.getCompanyurl());
            corp.put("categoryId", company.getCategoryId());
            corp.put("status", company.getStatus());

            response.put("status", 200);
            response.put("message", "Corporate login successful.");
            response.put("role", "corporate");
            response.put("company", corp);
            return ResponseEntity.ok(response);
        }

        // --- Step 3: Not found anywhere ---
        response.put("status", 404);
        response.put("message", "User not found.");
        return ResponseEntity.status(404).body(response);
    }

    /**
     * Check if email exists
     * GET /check-email?email=test@example.com
     */
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email) {
        Map<String, Object> response = usersServiceImpl.checkEmailResponse(email);

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Get all users
     * GET /users
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = usersServiceImpl.getAllUsersResponse();

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Get user by email
     * GET /user?email=test@example.com
     */
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@RequestParam String email) {
        Map<String, Object> response = usersServiceImpl.getUserByEmailResponse(email);

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    // PUT /user/{id}
    @PutMapping("/user/{id}")
   public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long id,
            @RequestBody Users updatedUser) {

        Map<String, Object> response = new HashMap<>();
        try {
            Users updated = usersService.updateUser(id, updatedUser);
            response.put("status", 200);
            response.put("message", "User updated successfully");
            response.put("data", updated);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", 404);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // deleteuser
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        logger.info("🗑️ Delete attempt for user ID: {}", id);

        Map<String, Object> response = usersService.deleteUser(id);

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    // show by id
 @GetMapping("/showuser/{id}")
     public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Users user = usersService.getUserById(id);

        if (user == null) {
            response.put("status", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("status", 200);
        response.put("message", "User retrieved successfully");
        response.put("data", user);

        return ResponseEntity.ok(response);
    }
}