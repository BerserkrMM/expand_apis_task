package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.entity.RoleEntity;

public interface RoleService{
    RoleEntity findByName(String name);
}
