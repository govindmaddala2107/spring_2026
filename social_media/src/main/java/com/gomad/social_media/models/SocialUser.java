package com.gomad.social_media.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class SocialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user")
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    /*
     This annotation help us to few things:
        1. It will make use of the attributes within join table. And there are few attributes like
            join column and inverse join column.
        2. JoinColumn is used to define the foreignKey for the entity where you're defining the relationship.
        3. Inverse Join Column that defines the foreign key for the other entity that you're defining the relationship.

        Finally:
        1. Name of the joint table is "user_group" and key for this table, which means like we're making use of join column
            - with joinColumns = @JoinColumn(name = "user_id"), we're saying that foreign key for the entity where we're
               defining relationship, where in social_users [Class here is SocialUser]
            - with inverseJoinColumns = @JoinColumn(name = "group_id"), it means foreign key for the other side of the relationship is group_id

    Ps: The relationship between the social group and social user is being managed by user_group table.

    So Many-to-Many relationship is between SocialUser and SocialGroup and
        - join table between them is user_group and
        - joint-column of SocialUser is user_id
        - joint-column of SocialGroup is group_id
    If we want the column name in the user_group table for the column name i.e representing SocialGroup

    Many-to-Many relationship can be managed by following process. We need a third table that needs to be created where this relationship is managed.

    */
    private Set<SocialGroup> socialGroups = new HashSet<>();
}
