package com.gomad.complete_tutorial.security.payload;

import com.gomad.complete_tutorial.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "username for login and password", example = "gomad")
    private String userName;

    @NotBlank(message = "Email shouldn't be empty.")
    @Size(max = 50)
    @Email
    @Schema(description = "email is needed for verification", example = "gomad@gmail.com")
    private String email;

    @NotBlank(message = "Password shouldn't be blank..!")
    @Schema(description = "Password needed for authentication", example = "Gomad@1234")
    private String password;

    @Schema(description = "Roles like USER or ADMIN in []", example = "['USER']")
    private Set<String> role;
}
