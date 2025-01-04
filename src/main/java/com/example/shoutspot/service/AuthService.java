package com.example.shoutspot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shoutspot.dto.AuthResponse;
import com.example.shoutspot.dto.LoginRequest;
import com.example.shoutspot.dto.SignupRequest;
import com.example.shoutspot.entity.User;
import com.example.shoutspot.enums.Role;
import com.example.shoutspot.jwt.JwtTokenProvider;
import com.example.shoutspot.jwt.PasswordEncoder;
import com.example.shoutspot.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse signup(SignupRequest request) throws Exception {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("User already exists. Please sign in instead.");
        }

        User newUser = new User();
        newUser.setFirstname(request.getFirstname());
        newUser.setEmail(request.getEmail());
        newUser.setIsGoogleUser(request.getGoogleSignUp());
        newUser.setRole(Role.USER);

        if (request.getGoogleSignUp()) {
            newUser.setGoogleUID(request.getGoogleUID());
        } else {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            newUser.setPassword(encodedPassword);
        }

        userRepository.save(newUser);
        String token = jwtTokenProvider.generateToken(newUser.getEmail(), newUser.getRole());
        AuthResponse authResponse = new AuthResponse("Sign-up successful.", token, newUser);
        return authResponse;
    }

    @Transactional
    public AuthResponse login(LoginRequest request) throws Exception {

        User user = null;
        if (request.getGoogleSignIn()) {
            user = userRepository.findByGoogleUID(request.getGoogleUID());
            if (user == null) {
                throw new EntityNotFoundException("Google user not found. Please sign up first.");
            }
        } else {
            user = userRepository.findByEmail(request.getEmail());
            if (user.getIsGoogleUser()) {
                throw new EntityNotFoundException("User Not Found. Please sign up first.");
            }

            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new EntityNotFoundException("Invalid credentials.");
            }
        }
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
        AuthResponse authResponse = new AuthResponse("Sign-in successful.", token, user);
        return authResponse;
    }
}

