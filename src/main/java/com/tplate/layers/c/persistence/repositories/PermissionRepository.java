package com.tplate.layers.c.persistence.repositories;

import com.tplate.layers.c.persistence.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
