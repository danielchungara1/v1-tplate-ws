package com.tplate.layers.business.validators;

import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.persistence.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleValidator {

    @Autowired
    RoleRepository roleRepository;

    public void guaranteeExistById(Long id ) throws RoleNotExistException {
        if (!this.roleRepository.existsById(id)) {
            log.error("Role not exist. {}", id);
            throw new RoleNotExistException();
        }
    }
}
