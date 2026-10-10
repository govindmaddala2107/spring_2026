package com.gomad.complete_tutorial.security.services;

import com.gomad.complete_tutorial.repositories.UserRepository;
import com.gomad.complete_tutorial.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Loading username = {}", username);

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> {
                    LOG.error("User not found: {}", username);
                    return new UsernameNotFoundException("User Not Found");
                });

        LOG.info("Found user: {}", user.getUserName());
        return UserDetailsImpl.build(user);
    }
}
