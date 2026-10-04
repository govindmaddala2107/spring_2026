package com.gomad.complete_tutorial.service;

import com.gomad.complete_tutorial.models.AppRole;
import com.gomad.complete_tutorial.models.Role;
import com.gomad.complete_tutorial.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Optional<Role> findByRoleName(AppRole roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
