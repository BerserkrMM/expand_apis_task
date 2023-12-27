package com.example.expand_apis_task.repository;

import com.example.expand_apis_task.model.Role;
import com.example.expand_apis_task.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
