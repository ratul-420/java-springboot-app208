package com.app208.app208.service;

import com.app208.app208.model.User;

public interface UserService {
    void saveUser(User user);
    User findByEmail(String email);
}
