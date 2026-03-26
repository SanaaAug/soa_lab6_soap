package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Base64;
import java.util.Optional;

@Endpoint
public class AuthEndpoint {

    private static final String NAMESPACE = "http://example.com/auth";

    private final UserRepository userRepository;

    @Value("${app.secret}")
    private String secretKey;

    public AuthEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==================== REGISTER ====================
    @PayloadRoot(namespace = NAMESPACE, localPart = "registerRequest")
    @ResponsePayload
    public RegisterResponse register(@RequestPayload RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();

        // Username шалгах
        if (userRepository.existsByUsername(request.getUsername())) {
            response.setSuccess(false);
            response.setMessage("Username already exists!");
            return response;
        }

        // Email шалгах
        if (userRepository.existsByEmail(request.getEmail())) {
            response.setSuccess(false);
            response.setMessage("Email already exists!");
            return response;
        }

        // User үүсгэх
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        response.setSuccess(true);
        response.setMessage("Registration successful!");
        response.setUserId(savedUser.getId());

        return response;
    }

    // ==================== LOGIN ====================
    @PayloadRoot(namespace = NAMESPACE, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("User not found!");
            return response;
        }

        User user = userOpt.get();

        // Password шалгах
        if (!user.getPassword().equals(hashPassword(request.getPassword()))) {
            response.setSuccess(false);
            response.setMessage("Wrong password!");
            return response;
        }

        // Token үүсгэх
        String token = generateToken(user.getId(), request.getUsername());

        response.setSuccess(true);
        response.setMessage("Login successful!");
        response.setToken(token);
        response.setUserId(user.getId());

        return response;
    }

    // ==================== VALIDATE TOKEN ====================
    @PayloadRoot(namespace = NAMESPACE, localPart = "validateTokenRequest")
    @ResponsePayload
    public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest request) {
        ValidateTokenResponse response = new ValidateTokenResponse();

        try {
            String decoded = new String(Base64.getDecoder().decode(request.getToken()));
            String[] parts = decoded.split(":");

            if (parts.length != 3) {
                response.setValid(false);
                return response;
            }

            Long userId = Long.parseLong(parts[0]);
            String username = parts[1];

            Optional<User> userOpt = userRepository.findById(userId);

            if (userOpt.isPresent() && userOpt.get().getUsername().equals(username)) {
                response.setValid(true);
                response.setUserId(userId);
                response.setUsername(username);
            } else {
                response.setValid(false);
            }

        } catch (Exception e) {
            response.setValid(false);
        }

        return response;
    }

//    private String generateToken(User user) {
//        String data = user.getId() + ":" + user.getUsername() + ":" + System.currentTimeMillis();
//        return Base64.getEncoder().encodeToString(data.getBytes());
//    }
    
    private String generateToken(Long userId, String username) {
    	System.out.println(username);
        String data = userId + ":" + username + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    private String hashPassword(String password) {
        return Base64.getEncoder().encodeToString((password + secretKey).getBytes());
    }
}