package com.tplate.layers.business.services;

import com.tplate.layers.business.exceptions.PermissionNotExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.PermissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Transactional
    public List<Permission> findAll() {
        return this.permissionRepository.findAll();
    }

    @Transactional
    public Permission getModelById(Long id) throws PermissionNotExistException {

        if (!this.permissionRepository.existsById(id)) {
            PermissionNotExistException.throwsException(id);
        }

        return this.permissionRepository.getOneById(id);

    }

}
