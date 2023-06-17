package com.group16.api.Service;

import java.util.List;

import com.group16.api.Entities.Post;

public interface PostService {
    public Post savePost(Post post);
    public List<Post> getAllPostsById (int id);
    public Post fetchPostsById (int id);
    public void deletePost (Integer postId);
}
