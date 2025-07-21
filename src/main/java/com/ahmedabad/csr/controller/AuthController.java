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
// @CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersServiceImpl usersServiceImpl; // For helper methods

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

    /**
     * Alternative register endpoint with /api/auth prefix
     * POST /api/auth/register
     */
    @PostMapping("/api/auth/register")
    public ResponseEntity<Map<String, Object>> registerWithPrefix(@RequestBody Map<String, String> userData) {
        logger.info("📝 Registration attempt for email: {}", userData.get("email"));

        Map<String, Object> response = usersService.registerUser(userData);

        // Return response with appropriate HTTP status
        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * User login
     * POST /login
     */
    // @PostMapping("/login")
    // public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String,
    // String> loginData) {
    // String email = loginData.get("email");
    // String password = loginData.get("password");

    // logger.info("🔐 Login attempt for email: {}", email);

    // Map<String, Object> response = usersService.loginUser(email, password);

    // // Return response with appropriate HTTP status
    // int status = (Integer) response.get("status");
    // return ResponseEntity.status(status).body(response);
    // }
   @PostMapping("/login")
public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
    String email = loginData.get("email");
    String password = loginData.get("password"); // may be null if caller forgot to send

    Map<String, Object> response = new HashMap<>();

    // --- Step 1: Try Users table ---
    Optional<Users> userOpt = usersService.getUserByEmailAndPassword(email, password);
    if (userOpt.isPresent()) {
        Users user = userOpt.get();
        response.put("status", 200);
        response.put("message", "Login successful.");
        response.put("role", user.getRole());   // e.g., department admin / super admin
        response.put("user", user);             // consider DTO if you want to hide password
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
     * Alternative login endpoint with /api/auth prefix
     * POST /api/auth/login
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<Map<String, Object>> loginWithPrefix(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        logger.info("🔐 Login attempt for email: {}", email);

        Map<String, Object> response = usersService.loginUser(email, password);

        // Return response with appropriate HTTP status
        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
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
     * Alternative check email endpoint
     * GET /api/auth/check-email?email=test@example.com
     */
    @GetMapping("/api/auth/check-email")
    public ResponseEntity<Map<String, Object>> checkEmailWithPrefix(@RequestParam String email) {
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
     * Alternative get all users endpoint
     * GET /api/auth/users
     */
    @GetMapping("/api/auth/users")
    public ResponseEntity<Map<String, Object>> getAllUsersWithPrefix() {
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

    /**
     * Alternative get user by email endpoint
     * GET /api/auth/user?email=test@example.com
     */
    @GetMapping("/api/auth/user")
    public ResponseEntity<Map<String, Object>> getUserByEmailWithPrefix(@RequestParam String email) {
        Map<String, Object> response = usersServiceImpl.getUserByEmailResponse(email);

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Update user
     * PUT /user/{id}
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id,
            @RequestBody Map<String, String> userData) {
        logger.info("📝 Update attempt for user ID: {}", id);

        Map<String, Object> response = usersService.updateUser(id, userData);

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Delete user
     * DELETE /user/{id}
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        logger.info("🗑️ Delete attempt for user ID: {}", id);

        Map<String, Object> response = usersService.deleteUser(id);

        int status = (Integer) response.get("status");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Test endpoint to verify controller is working
     * GET /test
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> response = Map.of(
                "status", 200,
                "message", "AuthController is working correctly!",
                "data", Map.of(
                        "timestamp", java.time.LocalDateTime.now().toString(),
                        "version", "1.0.0",
                        "endpoints", Map.of(
                                "register", "/registeruser or /api/auth/register",
                                "login", "/login or /api/auth/login",
                                "checkEmail", "/check-email or /api/auth/check-email",
                                "getUsers", "/users or /api/auth/users",
                                "getUser", "/user or /api/auth/user")));

        return ResponseEntity.ok(response);
    }
}