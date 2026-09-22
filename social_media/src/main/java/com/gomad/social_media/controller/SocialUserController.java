package com.gomad.social_media.controller;

import com.gomad.social_media.models.SocialUser;
import com.gomad.social_media.repository.SocialUserRepository;
import com.gomad.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SocialUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/socialusers")
    public ResponseEntity<List<SocialUser>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
