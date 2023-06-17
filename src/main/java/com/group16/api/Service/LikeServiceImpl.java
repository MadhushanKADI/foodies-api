package com.group16.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group16.api.Entities.FriendMap;
import com.group16.api.Entities.Like;
import com.group16.api.Entities.LikeList;
import com.group16.api.Entities.Status;
import com.group16.api.Repository.LikeRepository;

@Service
public class LikeServiceImpl implements LikeService{
    
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }



    @Override
    public FriendMap fetchFriendById(int userFriendId) {
        return likeRepository.fetchFriendById(userFriendId);
    }

    @Override
    public FriendMap fetchFriendbyBothID(int userId, int friendId) {
        return likeRepository.fetchFriendbyBothID(userId, friendId);
    }

    @Override
    public List<FriendMap> fetchAllFriendRequestById(int userId, Status status) {
        return likeRepository.fetchAllFriendRequestById(userId, status.ordinal());
    }

    @Override
    public List<FriendMap> fetchAllFriendFriendsById(int userId, Status status) {
        return likeRepository.fetchAllFriendFriendsById(userId, status.ordinal());
    }



    @Override
    public Like fetchbyUserAndPost(int userId, int postId) {
        return likeRepository.fetchbyUserIdAndPostId(userId, postId);
    }

    @Override
    public int getAllLikeCountByPostId(int postId) {
        return likeRepository.fetchAllLikeCountByPostId(postId);
    }

    @Override
    public int getLikeLikeCountByPostId(int postId) {
        return likeRepository.fetchLikeLikeCountByPostId(postId);
    }

    @Override
    public int getHeartLikeCountByPostId(int postId) {
        return likeRepository.fetchHeartLikeCountByPostId(postId);
    }

    @Override
    public List<LikeList> getAllLikeByPostId(int postId) {
        return likeRepository.listAdvanced(postId);
    }
}
