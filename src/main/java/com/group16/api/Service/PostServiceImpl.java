package com.group16.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group16.api.Entities.Post;
import com.group16.api.Repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{
    
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPostsById(int id) {
        return postRepository.fetchAllPostsById(id);
    }

    @Override
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post fetchPostsById(int id) {
        return postRepository.fetchPostsById(id);
    }

}
