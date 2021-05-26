package com.tplate.layers.business.shared;

import com.tplate.layers.business.exceptions.role.RoleNameNotExistException;
import com.tplate.layers.business.services.RoleService;
import com.tplate.layers.persistence.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RolesConfig {

    @Autowired
    RoleService roleService;

    public static Role ROLE_ADMIN;
    public static Role ROLE_VISUALIZER;

    @PostConstruct
    public void postConstruct() throws RoleNameNotExistException {
        ROLE_ADMIN =  this.roleService.getModelByName("ADMIN");
        ROLE_VISUALIZER =  this.roleService.getModelByName("VISUALIZER");
    }

}
