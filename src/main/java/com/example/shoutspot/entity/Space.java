package com.example.shoutspot.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Space")
@Data
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String spaceName;

    private String logo;
    private boolean squareLogo;

    @Column(nullable = false)
    private String spaceHeading;
    private String customMessage;

    @Column(nullable = false)
    private String collectionType;
    private boolean collectStarRatings;

    @Column(nullable = false)
    private String language;
    private String thankYouImage;
    private String thankYouTitle;
    private String thankYouMessage;
    private String redirectPageLink;
    private int maxVideoDuration;
    private int maxCharsAllowed;
    private String videoButtonText;
    private String textButtonText;
    private String consentText;
    private String textSubmissionTitle;
    private String questionLabel;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "space")
    private List<Review> reviews;

    @OneToMany(mappedBy = "space")
    private List<Question> questions;

    @OneToOne(mappedBy = "space")
    private CollectExtraInfo collectExtraInfo;
}
