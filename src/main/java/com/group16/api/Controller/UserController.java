package com.group16.api.Controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group16.api.Entities.User;
import com.group16.api.Service.UserService;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private Map<String, Object> attributes;
    private User user;

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userService.saveUser(user);
        
        return "New User is added";
    }

    @PostMapping("/edit")
    public ResponseEntity<JSONObject> edit(@RequestBody User req) {
        try {
            User user = req;
            LocalDateTime localDateTime = LocalDateTime.now();

            user.setModifiedDate(localDateTime.toString());

            userService.saveUser(user);
            
            JSONObject res = new JSONObject();
            res.put("content", "");
            res.put("isSuccessful", true);
            res.put("errorMsg", "");
            return ResponseEntity.status(HttpStatus.OK).body(res);

        } catch (Exception e) {
            JSONObject res = new JSONObject();
            res.put("content", "");
            res.put("isSuccessful", false);
            res.put("errorMsg", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/listSimple")
    public ResponseEntity<JSONObject> getAllUsers(@AuthenticationPrincipal OAuth2User principal) {
        try {
        this.attributes = principal.getAttributes();
        String email = this.attributes.get("email").toString();
        this.user = userService.fetchByEmail(email);

        JSONObject res = new JSONObject();
            res.put("content", user);
            res.put("isSuccessful", true);
            res.put("errorMsg", "");
            return ResponseEntity.status(HttpStatus.OK).body(res);

        } catch (Exception e) {
            JSONObject res = new JSONObject();
            res.put("content", "");
            res.put("isSuccessful", false);
            res.put("errorMsg", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }

    }

    @GetMapping("/delete")
    public ResponseEntity<JSONObject> deleteUserById(@AuthenticationPrincipal OAuth2User principal) {
        try{
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            Integer userId = Integer.valueOf(this.user.getUserId());
            userService.deleteUser(userId);

            JSONObject res = new JSONObject();
            res.put("content", userId);
            res.put("isSuccessful", true);
            res.put("errorMsg", "");
            return ResponseEntity.status(HttpStatus.OK).body(res);

        } catch (Exception e) {
            JSONObject res = new JSONObject();
            res.put("content", "");
            res.put("isSuccessful", false);
            res.put("errorMsg", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/user")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        return "Welcome, " + name + " (" + email + ")";
    }
    
    
}
