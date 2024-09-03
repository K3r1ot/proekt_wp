package com.example.proekt.service;

import com.example.proekt.model.User;

public interface UserService {
    User registerUser(String id, String username, String password);
    User findByUsername(String username);
}
