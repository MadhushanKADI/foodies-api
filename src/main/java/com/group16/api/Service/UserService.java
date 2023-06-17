package com.group16.api.Service;

import com.group16.api.Entities.User;

public interface UserService {
    public User saveUser(User user);
    public User fetchByEmail (String email);
    public void deleteUser (Integer userId);
}
