package com.gomad.complete_tutorial.service;

import com.gomad.complete_tutorial.models.User;
import com.gomad.complete_tutorial.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean existByEMail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
