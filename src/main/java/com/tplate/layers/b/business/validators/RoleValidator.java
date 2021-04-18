package com.tplate.layers.b.business.validators;

import com.tplate.layers.b.business.exceptions.RoleNotFoundException;
import com.tplate.layers.c.persistence.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleValidator {

    @Autowired
    RoleRepository roleRepository;

    public void guaranteeExistById(Long id ) throws RoleNotFoundException {
        if (!this.roleRepository.existsById(id)) {
            log.error("Role not exist. {}", id);
            throw new RoleNotFoundException();
        }
    }
}
