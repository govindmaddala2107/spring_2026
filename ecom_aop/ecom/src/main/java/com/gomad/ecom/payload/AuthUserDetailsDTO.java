package com.gomad.ecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDetailsDTO {

    private Long userId;
    private String username;
    private String email;
    private String roles;
}
