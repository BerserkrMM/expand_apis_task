package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.Role;
import com.example.expand_apis_task.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @PostConstruct
    private void fillRoles() {
        if (roleRepository.findAll().isEmpty()) {
            Role r1 = new Role();
            Role r2 = new Role();
            Role r3 = new Role();
            r1.setName("USER");
            r2.setName("ADMIN");
            r3.setName("GUEST");
            roleRepository.save(r1);
            roleRepository.save(r2);
            roleRepository.save(r3);
        }
    }

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }

}
