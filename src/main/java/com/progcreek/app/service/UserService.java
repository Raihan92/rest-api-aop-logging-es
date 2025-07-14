package com.progcreek.app.service;

import com.progcreek.app.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, User> userStore = new HashMap<>();

    public User getUserById(String id) {
        return userStore.get(id);
    }

    public User createUser(User user) {
        userStore.put(user.getId(), user);
        return user;
    }

    public User updateUser(String id, User user) {
        userStore.put(id, user);
        return user;
    }
}