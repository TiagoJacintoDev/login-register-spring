package com.loginregister.role;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public RoleModel save(RoleModel roleModel) {
        return roleRepository.save(roleModel);
    }

    public List<RoleModel> findAll() {
        return roleRepository.findAll();
    }

    public Optional<RoleModel> findById(UUID id) {
        return roleRepository.findById(id);
    }

    @Transactional
    public void delete(RoleModel roleModel) {
        roleRepository.delete(roleModel);
    }
}
