package com.gomad.ecom.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {

    private String username;
    private String email;
    private String password;
    private String roles;
}
