package com.example.shoutspot.dto;

import com.example.shoutspot.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String token;
    private User user;
}
