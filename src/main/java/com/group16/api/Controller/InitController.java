package com.group16.api.Controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group16.api.Entities.User;
import com.group16.api.Service.UserService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("")
public class InitController {
    @Autowired
    private UserService userService;
    
    private Map<String, Object> attributes;
    private User user;

    @GetMapping("")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        this.attributes = principal.getAttributes();
        LocalDateTime localDateTime = LocalDateTime.now();

        User newUser = new User();
        Boolean isNewUser = false;

        newUser.setName(this.attributes.get("name").toString());
        newUser.setEmail(this.attributes.get("email").toString());
        newUser.setPicture(this.attributes.get("picture").toString());

        this.user = userService.fetchByEmail(newUser.getEmail());

        if(this.user == null) {
            newUser.setCreatedDate(localDateTime.toString());
            User addedUser = userService.saveUser(newUser);
            isNewUser = true;
            return "isNewUser: " + isNewUser + " (" + addedUser.getEmail() + ")";
        }
        else{
            isNewUser = false;
            return "isNewUser: " + isNewUser + " (" + this.user.getEmail() + ")";
        }
    }
    
    
}
