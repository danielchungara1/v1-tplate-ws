package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission getOneById(Long id);

}
