package com.example.shoutspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoutspot.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    Boolean existsByEmail(String email);
    User findByGoogleUID(String googleUID);
}
