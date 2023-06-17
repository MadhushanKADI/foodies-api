package com.group16.api.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FriendMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userFriendId;
    private int userId;
    private int friendId;
    private Status status;
    private String createdDate;
    private String modifiedDate;

    public FriendMap() {
        
    }

    public int getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(int userFriendId) {
        this.userFriendId = userFriendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}