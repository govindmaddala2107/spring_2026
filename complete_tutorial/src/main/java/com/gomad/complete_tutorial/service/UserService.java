package com.gomad.complete_tutorial.service;

import com.gomad.complete_tutorial.models.User;
import com.gomad.complete_tutorial.security.payload.SignupRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface UserService {
    boolean existByUsername(@Valid @NotBlank String username);
    boolean existByEMail(@Valid @NotBlank String email);
    User registerUser(User user);
}
