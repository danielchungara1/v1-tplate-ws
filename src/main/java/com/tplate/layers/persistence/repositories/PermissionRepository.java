package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission getOneById(Long id);

    @Query ("select p from Permission p " +
            "where LOWER(p.name)  like %:text% " +
            "or LOWER(p.description) like %:text%")
    Page findAll(Pageable pageable, String text);

}
