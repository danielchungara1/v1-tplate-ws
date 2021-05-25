package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getOneById(Long id);

    Boolean existsByName(String name);

}
