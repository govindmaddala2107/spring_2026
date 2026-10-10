package com.gomad.social_media.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gomad.social_media.models.SocialUser;
import com.gomad.social_media.service.UserService;

@RestController
public class SocialUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/socialusers")
    public ResponseEntity<List<SocialUser>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/socialusers/add")
    public ResponseEntity<SocialUser> addSocialUser(@RequestBody SocialUser socialUser) {
        return new ResponseEntity<>(userService.addSocialUser(socialUser), HttpStatus.OK);
    }

    @DeleteMapping("/socialusers/{id}")
    public ResponseEntity<String> deleteSocialUser(@PathVariable Long id){
        SocialUser user = userService.deleteSocialUser(id);
        return new ResponseEntity<>("User is deleted successfully", HttpStatus.OK);
    }
}
