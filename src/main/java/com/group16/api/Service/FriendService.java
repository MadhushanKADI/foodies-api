package com.group16.api.Service;

import java.util.List;

import com.group16.api.Entities.FriendMap;
import com.group16.api.Entities.Status;

public interface FriendService {
    public FriendMap saveFriend(FriendMap friendMap);
    public FriendMap fetchFriendById (int userFriendId);
    public FriendMap fetchFriendbyBothID(int userId, int friendId);
    public List<FriendMap> fetchAllFriendRequestById(int userId, Status status);
    public List<FriendMap> fetchAllFriendFriendsById(int userId, Status status);
}
