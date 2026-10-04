package com.gomad.complete_tutorial.service;

import com.gomad.complete_tutorial.models.AppRole;
import com.gomad.complete_tutorial.models.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(AppRole roleName);
}
