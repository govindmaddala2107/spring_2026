package com.gomad.social_media.models;

import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Means Many Posts mapped to one SocialUser
    @JoinColumn(name = "user_id")
    private SocialUser socialUser;
}
