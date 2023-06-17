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

import com.group16.api.Entities.FriendMap;
import com.group16.api.Entities.Status;
import com.group16.api.Entities.User;
import com.group16.api.Service.FriendService;
import com.group16.api.Service.UserService;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;
    
    private Map<String, Object> attributes;
    private User user;

    @PostMapping("/sendRequest")
    public ResponseEntity<JSONObject> sendRequest(@RequestParam("friendId") int friendId, @AuthenticationPrincipal OAuth2User principal) {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            FriendMap checkFriend = friendService.fetchFriendbyBothID(user.getUserId(), friendId);
            
            FriendMap friend = new FriendMap();

            if (checkFriend == null) {
                friend.setUserId(this.user.getUserId());
                friend.setFriendId(friendId);
                friend.setStatus(Status.Requested);
                friend.setCreatedDate(localDateTime.toString());

                friendService.saveFriend(friend);
            }
            else {
                checkFriend.setStatus(Status.Requested);
                checkFriend.setModifiedDate(localDateTime.toString());

                friendService.saveFriend(checkFriend);
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

    @PostMapping("/acceptFriend")
    public ResponseEntity<JSONObject> acceptFriend(@RequestParam("userFriendId") int userFriendId) {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            FriendMap friend = friendService.fetchFriendById(userFriendId);

            friend.setStatus(Status.Friends);
            friend.setModifiedDate(localDateTime.toString());

            friendService.saveFriend(friend);
            
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

    @PostMapping("/unfriendFriend")
    public ResponseEntity<JSONObject> unfriendFriend(@RequestParam("friendId") int friendId, @AuthenticationPrincipal OAuth2User principal) {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            FriendMap friend = friendService.fetchFriendbyBothID(user.getUserId(), friendId);

            friend.setStatus(Status.Unfriend);
            friend.setModifiedDate(localDateTime.toString());

            friendService.saveFriend(friend);
            
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

    @GetMapping("/requestListSimple")
    public ResponseEntity<JSONObject> getAllRequest(@AuthenticationPrincipal OAuth2User principal) {
        try{
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            List<FriendMap> data = friendService.fetchAllFriendRequestById(this.user.getUserId(), Status.Requested);

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

    @GetMapping("/friendListSimple")
    public ResponseEntity<JSONObject> getAllFriends(@AuthenticationPrincipal OAuth2User principal) {
        try{
            this.attributes = principal.getAttributes();
            String email = this.attributes.get("email").toString();
            this.user = userService.fetchByEmail(email);

            List<FriendMap> data = friendService.fetchAllFriendFriendsById(this.user.getUserId(), Status.Friends);

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
