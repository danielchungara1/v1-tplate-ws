package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.role.RoleDto;
import com.tplate.layers.business.exceptions.RoleNameExistException;
import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.persistence.models.Role;
import com.tplate.layers.persistence.repositories.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public Role getModelById(Long id)  throws RoleNotExistException {

        if (id == null) { RoleNotExistException.throwsException(id); }

        return this.roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotExistException(id));

    }

    @Transactional
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Role saveModel(RoleDto dto) throws RoleNameExistException {

        Role role = Role.builder().build();

        return this.saveOrUpdateModel(role, dto);
    }

    private Role saveOrUpdateModel(Role role, RoleDto dto) throws RoleNameExistException {

        // Role Name
        if (this.roleRepository.existsByName(dto.getName())) {
            RoleNameExistException.throwsException(dto.getName());
        }
        role.setName(dto.getName());

        // Role Description
        role.setDescription(dto.getDescription());

        return this.roleRepository.save(role);

    }

}
