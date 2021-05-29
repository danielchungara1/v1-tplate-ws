package com.tplate.layers.business.services;

import com.tplate.layers.access.filters.SearchText;
import com.tplate.layers.business.exceptions.permission.PermissionNotExistException;
import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.repositories.PermissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class PermissionService {

    @Autowired
    PermissionRepository repository;

    @Transactional
    public List<Permission> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public Permission getModelById(Long id) throws PermissionNotExistException {

        if (!this.repository.existsById(id)) {
            PermissionNotExistException.throwsException(id);
        }

        return this.repository.getOneById(id);

    }

    @Transactional
    public Page find(Pageable pageable, SearchText searchText) {
        return this.repository.findAll(pageable, searchText.getText());
    }
}
