package com.group16.api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group16.api.Entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    
    // Custom SQL query to get all posts by clientId
    @Query(value = "SELECT * FROM post WHERE user_Id = :userId", nativeQuery = true)
    List<Post> fetchAllPostsById(@Param("userId") int userId);

    // Custom SQL query to get post by clientId
    @Query(value = "SELECT * FROM post WHERE post_id = :postId", nativeQuery = true)
    Post fetchPostsById(@Param("postId") int postId);
}
