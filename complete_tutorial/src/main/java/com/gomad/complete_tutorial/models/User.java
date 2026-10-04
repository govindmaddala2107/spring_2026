package com.gomad.complete_tutorial.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username", "email"})
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username shouldn't be blank.")
    @Size(max = 200, message = "Username size shouldn't more than 20 characters.")
    @Column(name = "username")
    private String userName;

    @NotBlank(message = "Email shouldn't be blank.")
    @Size(max = 250, message = "Email size shouldn't more than 50 characters.")
    private String email;

    @NotBlank(message = "Password shouldn't be blank.")
    @Size(min = 4, max = 120, message = "Password size shouldn't more than 120 characters and less than 8.")
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Getter
    @Setter
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    // It creates a table named user_role with 2 columns user_id and role_id.
    private Set<Role> roles = new HashSet<>();
}

