package com.tplate.layers.b.business.services;

import com.tplate.layers.b.business.exceptions.RoleNotFoundException;
import com.tplate.layers.b.business.validators.RoleValidator;
import com.tplate.layers.c.persistence.models.Role;
import com.tplate.layers.c.persistence.repositories.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleValidator roleValidator;


    @Transactional
    public Role getModelById(Long id)  throws RoleNotFoundException{

        this.roleValidator.guaranteeExistById(id);

        return this.roleRepository.getOne(id);
    }

    @Transactional
    public void guaranteeExistById(Long id ) throws RoleNotFoundException {
        if (!this.roleRepository.existsById(id)) {
            log.error("Role not found. {}", id);
            throw new RoleNotFoundException();
        }
    }

}