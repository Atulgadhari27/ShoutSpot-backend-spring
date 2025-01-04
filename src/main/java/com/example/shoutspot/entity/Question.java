package com.example.shoutspot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "spaceId", referencedColumnName = "id")
    private Space space;
}

