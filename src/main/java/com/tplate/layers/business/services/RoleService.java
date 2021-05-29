package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.role.RoleDto;
import com.tplate.layers.access.filters.SearchText;
import com.tplate.layers.business.exceptions.permission.PermissionNotExistException;
import com.tplate.layers.business.exceptions.role.*;
import com.tplate.layers.business.shared.RolesConfig;
import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.models.Role;
import com.tplate.layers.persistence.repositories.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RoleService {

    @Autowired
    RoleRepository repository;

    @Autowired
    PermissionService permissionService;

    @Transactional
    public Role getModelById(Long id)  throws RoleNotExistException {

        if (id == null) { RoleNotExistException.throwsException(id); }

        return this.repository.findById(id)
                .orElseThrow(() -> new RoleNotExistException(id));

    }

    @Transactional
    public List<Role> findAll() {
        return this.repository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Role saveModel(RoleDto dto) throws RoleNameExistException, RoleWithoutPermissionsException, PermissionNotExistException {

        // Role Name
        if (this.repository.existsByName(dto.getName())) {
            RoleNameExistException.throwsException(dto.getName());
        }

        Role role = Role.builder().build();
        role.setName(dto.getName());

        return this.saveOrUpdateModel(role, dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Role updateModel(RoleDto dto, Long roleId) throws RoleNameExistException, RoleWithoutPermissionsException, PermissionNotExistException, RoleNotExistException {

        Role role = this.getModelById(roleId);

        // Name False Positive.
        if ( !this.repository.existsByName(dto.getName()) ) {
            role.setName(dto.getName());
        } else {
            if (role.getName().equals(dto.getName())) {
                // False Positive
            } else {
                RoleNameExistException.throwsException(dto.getName());
            }
        }

        return this.saveOrUpdateModel(role, dto);
    }


    private Role saveOrUpdateModel(Role role, RoleDto dto) throws RoleNameExistException, RoleWithoutPermissionsException, PermissionNotExistException {

        // Role Description
        role.setDescription(dto.getDescription());

        // Role Permissions
        if (dto.getPermissionIds().isEmpty()) {
            RoleWithoutPermissionsException.throwsException(dto.getPermissionIds());
        }
        List<Permission> permissions = new ArrayList<>();
        for (Long permissionId: dto.getPermissionIds()) {
            Permission p = this.permissionService.getModelById(permissionId);
            permissions.add(p);
        }
        role.setPermissions(permissions);

        return this.repository.save(role);

    }

    @Transactional
    public void deleteModelById(Long id) throws RoleNotExistException, RoleMustNotBeDeletedException {

        // Validations
        if (!this.repository.existsById(id)) {
            RoleNotExistException.throwsException(id);
        }
        // THE ADMIN AND VISUALIZER ROLE MUSTN'T BE DELETED
        if (id.equals(RolesConfig.ROLE_ADMIN.getId())) {
            RoleMustNotBeDeletedException.throwsException(RolesConfig.ROLE_ADMIN.getName());
        }
        if (id.equals(RolesConfig.ROLE_VISUALIZER.getId())) {
            RoleMustNotBeDeletedException.throwsException(RolesConfig.ROLE_VISUALIZER.getName());
        }


        if (this.repository.roleIdHasAnyUserAssigned(id)) {
            this.repository.assignRoleVisualizerAllUserThatHaveRoleId(id);
        }
        this.repository.deleteById(id);

    }

    @Transactional
    public Role getModelByName(String name) throws RoleNameNotExistException {

        if (!this.repository.existsByName(name)) {
            RoleNameNotExistException.throwsException(name);
        }

        return this.repository.getByName(name);

    }


    @Transactional
    public Page find(Pageable pageable, SearchText searchText) {
        return this.repository.findAll(pageable, searchText.getText());
    }
}
