package com.group16.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group16.api.Entities.FriendMap;
import com.group16.api.Entities.Status;
import com.group16.api.Repository.FriendRepository;

@Service
public class FriendServiceImpl implements FriendService{
    
    @Autowired
    private FriendRepository friendRepository;

    @Override
    public FriendMap saveFriend(FriendMap friendMap) {
        return friendRepository.save(friendMap);
    }

    @Override
    public FriendMap fetchFriendById(int userFriendId) {
        return friendRepository.fetchFriendById(userFriendId);
    }

    @Override
    public FriendMap fetchFriendbyBothID(int userId, int friendId) {
        return friendRepository.fetchFriendbyBothID(userId, friendId);
    }

    @Override
    public List<FriendMap> fetchAllFriendRequestById(int userId, Status status) {
        return friendRepository.fetchAllFriendRequestById(userId, status.ordinal());
    }

    @Override
    public List<FriendMap> fetchAllFriendFriendsById(int userId, Status status) {
        return friendRepository.fetchAllFriendFriendsById(userId, status.ordinal());
    }
}
