package com.gomad.ecom.security.services;

import com.gomad.ecom.models.User;
import com.gomad.ecom.payload.AuthUserDetailsDTO;
import com.gomad.ecom.repositories.UserRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuthUserDetails {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthUserDetailsDTO getUserDetails(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            String username = auth.getName();
            Optional<User> user = userRespository.findByUsername(username);
            return user.map(value -> modelMapper.map(value, AuthUserDetailsDTO.class)).orElse(null);
        }
        return null;
    }

    public boolean isAdmin(){
        AuthUserDetailsDTO user = getUserDetails();
        if(user == null) return false;
        return user.getRoles().contains("ADMIN");
    }
}
