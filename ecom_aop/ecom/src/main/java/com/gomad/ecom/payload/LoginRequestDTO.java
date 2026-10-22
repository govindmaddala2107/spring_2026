package com.gomad.ecom.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Username shouldn't be blank.")
    private String username;

    @NotBlank(message = "Password shouldn't be blank.")
    private String password;
}
