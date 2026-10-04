package com.gomad.complete_tutorial.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String token;
    private String username;
    private List<String> roles;
}
