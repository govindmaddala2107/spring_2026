package com.gomad.social_media.service;

import com.gomad.social_media.models.SocialUser;
import com.gomad.social_media.repository.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private SocialUserRepository  socialUserRepository;

    public List<SocialUser> findAll(){
        return socialUserRepository.findAll();
    }
}
