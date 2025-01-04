package com.example.shoutspot.entity;
import java.util.List;

import com.example.shoutspot.enums.Role;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, name = "firstname")
    private String firstname;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "`googleUID`")
    private String googleUID;

    @Column(nullable = false, name = "`isGoogleUser`")
    private Boolean isGoogleUser;

    @Column(nullable = false, name = "role")
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user")
    private List<Space> spaces;
}
