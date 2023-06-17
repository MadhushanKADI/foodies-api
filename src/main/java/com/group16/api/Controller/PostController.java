package com.group16.api.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group16.api.Entities.Post;
import com.group16.api.Entities.User;
import com.group16.api.Service.PostService;
import com.group16.api.Service.UserService;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    
    private Map<String, Object> attributes;
    private User user;

    @PostMapping("/add")
    public ResponseEntity<JSONObject> add(@RequestBody Post req, @AuthenticationPrincipal OAuth2User principal) {
        try {
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);
            
            Post post = req;
            LocalDateTime localDateTime = LocalDateTime.now();

            post.setUserId(this.user.getUserId());
            post.setCreatedDate(localDateTime.toString());

            postService.savePost(post);
            
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

    @PostMapping("/edit")
    public ResponseEntity<JSONObject> edit(@RequestBody Post req) {
        try {
            Post post = req;
            LocalDateTime localDateTime = LocalDateTime.now();

            post.setModifiedDate(localDateTime.toString());

            postService.savePost(post);
            
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
    public ResponseEntity<JSONObject> getAllPosts(@AuthenticationPrincipal OAuth2User principal) {
        try{
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            List<Post> data = postService.getAllPostsById(this.user.getUserId());

            JSONObject res = new JSONObject();
            res.put("content", data);
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
    public ResponseEntity<JSONObject> deletePostById(int postIdReq) {
        try{
            Integer postId = Integer.valueOf(postIdReq);
            postService.deletePost(postId);

            JSONObject res = new JSONObject();
            res.put("content", postId);
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
}
