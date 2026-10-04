package com.gomad.complete_tutorial.repositories;

import com.gomad.complete_tutorial.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
            SELECT u
            FROM User u
            LEFT JOIN FETCH u.roles
            WHERE u.userName = :username
            """)
    Optional<User> findByUserName(String username);

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);
}
