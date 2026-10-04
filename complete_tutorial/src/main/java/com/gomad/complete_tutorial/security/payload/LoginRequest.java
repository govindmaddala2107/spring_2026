package com.gomad.complete_tutorial.security.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username shouldn't be blank..!")
    private String userName;

    @NotBlank(message = "Password shouldn't be blank..!")
    private String password;
}
