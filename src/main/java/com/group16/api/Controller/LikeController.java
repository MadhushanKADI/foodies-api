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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group16.api.Entities.Like;
import com.group16.api.Entities.LikeList;
import com.group16.api.Entities.User;
import com.group16.api.Service.LikeService;
import com.group16.api.Service.UserService;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;
    
    private Map<String, Object> attributes;
    private User user;

    @PostMapping("/add")
    public ResponseEntity<JSONObject> add(@RequestBody Like req, @AuthenticationPrincipal OAuth2User principal) {
        try {
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);
            
            Like like = req;
            LocalDateTime localDateTime = LocalDateTime.now();

            Like previousLike = likeService.fetchbyUserAndPost(this.user.getUserId(), req.getPostId());
            if (previousLike == null){
                like.setUserId(this.user.getUserId());
                like.setCreatedDate(localDateTime.toString());

                likeService.saveLike(like);
            }
            else {
                previousLike.setReaction(req.getReaction());
                previousLike.setModifiedDate(localDateTime.toString());

                likeService.saveLike(previousLike);
            }
            
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
    public ResponseEntity<JSONObject> getLikesCount(@RequestParam int postId, @AuthenticationPrincipal OAuth2User principal) {
        try{
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            int totCount = likeService.getAllLikeCountByPostId(postId);
            int likeCount = likeService.getLikeLikeCountByPostId(postId);
            int heartCount = likeService.getHeartLikeCountByPostId(postId);

            JSONObject counts = new JSONObject();
            counts.put("totalCount", totCount);
            counts.put("likeCount", likeCount);
            counts.put("heartCount", heartCount);

            JSONObject res = new JSONObject();
            res.put("content", counts);
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

    @GetMapping("/listAdvanced")
    public ResponseEntity<JSONObject> getAllLikes(@RequestParam int postId, @AuthenticationPrincipal OAuth2User principal) {
        try{
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            List<LikeList> data = likeService.getAllLikeByPostId(postId);

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
}
