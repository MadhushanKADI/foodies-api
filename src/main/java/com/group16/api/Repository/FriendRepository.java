package com.group16.api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group16.api.Entities.FriendMap;

@Repository
public interface FriendRepository extends JpaRepository<FriendMap,Integer> {

    // Custom SQL query to get friend by id
    @Query(value = "SELECT * FROM friend_map WHERE user_friend_id = :userFriendId", nativeQuery = true)
    FriendMap fetchFriendById(@Param("userFriendId") int userFriendId);

    // Custom SQL query to check if friend exist
    @Query(value = "SELECT * FROM friend_map WHERE user_id = :userId AND friend_id = :friendId", nativeQuery = true)
    FriendMap fetchFriendbyBothID(@Param("userId") int userId, @Param("friendId") int friendId);

    @Query(value = "SELECT * FROM friend_map WHERE friend_Id = :userId AND status = :status", nativeQuery = true)
    List<FriendMap> fetchAllFriendRequestById(@Param("userId") int userId, @Param("status") int status);

    @Query(value = "SELECT * FROM friend_map WHERE (user_id = :userId OR friend_id = :userId) AND status = :status", nativeQuery = true)
    List<FriendMap> fetchAllFriendFriendsById(@Param("userId") int userId, @Param("status") int status);

}
