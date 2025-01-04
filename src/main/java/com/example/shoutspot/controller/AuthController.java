package com.example.shoutspot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoutspot.dto.AuthResponse;
import com.example.shoutspot.dto.LoginRequest;
import com.example.shoutspot.dto.SignupRequest;
import com.example.shoutspot.service.AuthService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String healthCheck() {
        return new String("Hello, World!");
    }
    
    @PostMapping("api/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            if (!request.getGoogleSignUp() && request.getPassword().length() == 0) {
                throw new ConstraintViolationException("Password is required for normal sign-up.", null);
            }
            AuthResponse authResponse = authService.signup(request);
            return ResponseEntity.ok(authResponse);
        } catch (EntityExistsException e) {
            logger.debug("User already exists: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("AuthController.signup: Error signing up user: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request.getGoogleSignIn()) {
                if (request.getGoogleUID() == null || request.getGoogleUID().length() == 0) {
                    throw new ConstraintViolationException("Google UID is required for Google sign-in.", null);
                }
            } else {
                if (!StringUtils.hasText(request.getPassword()) || !StringUtils.hasText(request.getEmail())) {
                    throw new ConstraintViolationException("Email and password are required for sign-in.", null); 
                }
            }
            AuthResponse authResponse = authService.login(request);
            return ResponseEntity.ok(authResponse);
        } catch (EntityNotFoundException e) {
            logger.debug("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }  catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("AuthController.login: Error while logging in " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
