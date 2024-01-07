package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.model.entity.RoleEntity;
import com.example.expand_apis_task.repository.RoleRepository;
import com.example.expand_apis_task.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {

    @PostConstruct
    private void fillRoles() {
        if (roleRepository.findAll().isEmpty()) {
            RoleEntity r1 = new RoleEntity();
            RoleEntity r2 = new RoleEntity();
            RoleEntity r3 = new RoleEntity();
            r1.setName("ROLE_USER");
            r2.setName("ROLE_ADMIN");
            r3.setName("ROLE_GUEST");
            roleRepository.saveAll(List.of(r1, r2, r3));
        }
    }

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No record for role: " + name));
    }
}
