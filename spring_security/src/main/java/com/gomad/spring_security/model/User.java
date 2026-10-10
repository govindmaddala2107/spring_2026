package com.gomad.spring_security.model;

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
        @UniqueConstraint(columnNames = { "username", "email" })
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username shouldn't be blank.")
    @Size(max = 20, message = "Username size shouldn't more than 20 characters.")
    private String username;

    @NotBlank(message = "Email shouldn't be blank.")
    @Size(max = 50, message = "Email size shouldn't more than 50 characters.")
    private String email;

    @NotBlank(message = "Password shouldn't be blank.")
    @Size(min = 8, max = 120, message = "Password size shouldn't more than 120 characters and less than 8.")
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Getter
    @Setter
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    // It creates a table named user_role with 2 columns user_id and role_id.
    private Set<Role> roles = new HashSet<>();
}
