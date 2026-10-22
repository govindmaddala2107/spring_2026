package com.gomad.ecom.services;

import com.gomad.ecom.models.User;
import com.gomad.ecom.payload.CustomApiResponse;
import com.gomad.ecom.payload.LoginRequestDTO;
import com.gomad.ecom.payload.SignupRequestDTO;
import com.gomad.ecom.repositories.UserRespository;
import com.gomad.ecom.security.jwt.JwtUtils;
import com.gomad.ecom.security.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomApiResponse registerUser(SignupRequestDTO signupUser) {
        try {
            if(userRespository.findByUsername(signupUser.getUsername()).isPresent()){
                return new CustomApiResponse("Username is already taken.", false, HttpStatus.FOUND);
            }
            if(userRespository.findByEmail(signupUser.getEmail()).isPresent()){
                return new CustomApiResponse("Email is already taken.", false, HttpStatus.FOUND);
            }
            User user = modelMapper.map(signupUser, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRespository.save(user);
            return new CustomApiResponse("User is registered successfully.", true, HttpStatus.OK);
        }catch (Exception e){
            logger.debug("Error while signup {}", e.getMessage());
            return new CustomApiResponse(e.getMessage(), false, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean signIn(LoginRequestDTO credentials) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    )
            );
        } catch (AuthenticationException ex){
            logger.debug("Error while sign-in {}", ex.getMessage());
            return false;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}
