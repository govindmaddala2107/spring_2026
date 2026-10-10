package com.gomad.social_media.service;

import com.gomad.social_media.models.SocialUser;
import com.gomad.social_media.repository.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private SocialUserRepository  socialUserRepository;

    public List<SocialUser> findAll(){
        return socialUserRepository.findAll();
    }

    public SocialUser addSocialUser(SocialUser socialUser){
        return socialUserRepository.save(socialUser);
    }

    public SocialUser deleteSocialUser(Long id){
        SocialUser socialUser = socialUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Social User Not Found"));
        socialUserRepository.delete(socialUser);
        return socialUser;
    }
}
