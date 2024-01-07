package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.dto.UserDTO;

public interface UserService {

    void addUser(UserDTO userDTO);

    String authenticateUser(UserDTO userDTO);
}
