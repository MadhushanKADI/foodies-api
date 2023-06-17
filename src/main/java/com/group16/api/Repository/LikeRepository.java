package com.group16.api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group16.api.Entities.FriendMap;
import com.group16.api.Entities.Like;
import com.group16.api.Entities.LikeList;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {

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



    @Query(value = "SELECT * FROM like WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    Like fetchbyUserIdAndPostId(@Param("userId") int userId, @Param("postId") int postId);

    @Query(value = "SELECT * FROM like WHERE post_id = :postId AND reaction <> 1", nativeQuery = true)
    int fetchAllLikeCountByPostId(@Param("postId") int postId);

    @Query(value = "SELECT * FROM like WHERE post_id = :postId AND reaction = 2", nativeQuery = true)
    int fetchLikeLikeCountByPostId(@Param("postId") int postId);

    @Query(value = "SELECT * FROM like WHERE post_id = :postId AND reaction = 3", nativeQuery = true)
    int fetchHeartLikeCountByPostId(@Param("postId") int postId);

    @Query(value = "SELECT u.user_id, u.name, l.reaction, l.created_date, l.modified_date FROM like l INNER JOIN user u ON l.user_id = u.user_id WHERE post_id = :postId AND reaction <> 1", nativeQuery = true)
    List<LikeList> listAdvanced(@Param("postId") int postId);

}
