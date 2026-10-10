package com.gomad.complete_tutorial.utils;

import com.gomad.complete_tutorial.models.User;
import com.gomad.complete_tutorial.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    private UserRepository userRepository;

    public String getUserMail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            User user = userRepository.findByUserName(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return user.getEmail();
        }
        return null;
    }

    public String getUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            User user = userRepository.findByUserName(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return user.getUserName();
        }
        return null;
    }
}
