package com.gomad.ecom.services;

import com.gomad.ecom.payload.CustomApiResponse;
import com.gomad.ecom.payload.LoginRequestDTO;
import com.gomad.ecom.payload.SignupRequestDTO;

public interface UserService {
    CustomApiResponse registerUser(SignupRequestDTO user);
    boolean signIn(LoginRequestDTO credentials);
}
