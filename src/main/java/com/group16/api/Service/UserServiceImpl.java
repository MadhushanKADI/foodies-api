package com.group16.api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group16.api.Entities.User;
import com.group16.api.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User fetchByEmail(String email) {
        User user = userRepository.getUsersByEmail(email);
        return user;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

}
