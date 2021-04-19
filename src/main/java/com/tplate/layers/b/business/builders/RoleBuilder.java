package com.tplate.layers.b.business.builders;

import com.tplate.layers.b.business.exceptions.RoleNotFoundException;
import com.tplate.layers.b.business.validators.RoleValidator;
import com.tplate.layers.c.persistence.models.User;
import com.tplate.layers.c.persistence.models.Role;
import com.tplate.layers.c.persistence.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RoleBuilder {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleValidator roleValidator;

    public Role buildModel(Long id) throws RoleNotFoundException {

        this.roleValidator.guaranteeExistById(id);

        return  this.roleRepository.getOne(id);

    }

}