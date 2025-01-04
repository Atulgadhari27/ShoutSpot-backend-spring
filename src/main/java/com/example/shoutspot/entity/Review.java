package com.example.shoutspot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;

    @Column(nullable = false)
    private String reviewType;
    private int positiveStarsCount;
    private String reviewText;
    private String reviewImage;
    private String reviewVideo;

    @Lob
    private String userDetails;

    private boolean isLiked;
    private LocalDateTime submitDateTime;
    private boolean isSpam;
    private String sentiment;

    @ManyToOne
    @JoinColumn(name = "spaceId", referencedColumnName = "id")
    private Space space;
}

