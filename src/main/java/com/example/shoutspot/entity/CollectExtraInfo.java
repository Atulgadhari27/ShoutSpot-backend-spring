package com.example.shoutspot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CollectExtraInfo")
@Data
public class CollectExtraInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean name;
    private boolean email;
    private boolean company;
    private boolean socialLink;
    private boolean address;

    @OneToOne
    @JoinColumn(name = "spaceId", referencedColumnName = "id")
    private Space space;
}

