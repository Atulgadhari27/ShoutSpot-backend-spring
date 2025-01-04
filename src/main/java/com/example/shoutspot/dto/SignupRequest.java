package com.example.shoutspot.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotEmpty(message = "Firstname is required.")
    private String firstname;
    @NotEmpty(message = "Email is required.")
    private String email;
    private String password = "";
    private String googleUID;
    private Boolean googleSignUp = false;

}
