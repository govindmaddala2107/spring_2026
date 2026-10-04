package com.gomad.complete_tutorial.security.payload;

import com.gomad.complete_tutorial.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Username shouldn't be empty.")
    @Size(min = 3, max = 20)
    private String userName;

    @NotBlank(message = "Email shouldn't be empty.")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "Password shouldn't be blank..!")
    private String password;

    private Set<String> role;
}
