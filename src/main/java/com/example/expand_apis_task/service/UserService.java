package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.UserEntity;

import java.util.List;

public interface UserService {
    boolean existByName(String username);

    void save(UserEntity user);

    List<UserEntity> getAll();
}
