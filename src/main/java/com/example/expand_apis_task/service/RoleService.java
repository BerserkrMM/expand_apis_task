package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.Role;

public interface RoleService{
    Role findByName(String name);
}
