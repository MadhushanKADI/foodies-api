package com.group16.api.Service;

import java.util.List;

import com.group16.api.Entities.FriendMap;
import com.group16.api.Entities.Like;
import com.group16.api.Entities.LikeList;
import com.group16.api.Entities.Status;

public interface LikeService {
    public Like saveLike(Like like);


    public FriendMap fetchFriendById (int userFriendId);
    public FriendMap fetchFriendbyBothID(int userId, int friendId);
    public List<FriendMap> fetchAllFriendRequestById(int userId, Status status);
    public List<FriendMap> fetchAllFriendFriendsById(int userId, Status status);

    public Like fetchbyUserAndPost(int userId, int postId);
    public int getAllLikeCountByPostId(int postId);
    public int getLikeLikeCountByPostId(int postId);
    public int getHeartLikeCountByPostId(int postId);
    public List<LikeList> getAllLikeByPostId(int postId);
}
