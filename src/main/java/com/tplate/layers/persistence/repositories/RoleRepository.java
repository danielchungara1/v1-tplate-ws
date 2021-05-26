package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getOneById(Long id);

    Boolean existsByName(String name);

    @Query(
            value = "SELECT CASE WHEN EXISTS ( SELECT * FROM {h-schema}user WHERE role_id = :roleId) " +
                    "THEN CAST(1 AS BIT) " +
                    "ELSE CAST(0 AS BIT) END",
            nativeQuery = true)
    Boolean roleIdHasAnyUserAssigned(@Param("roleId") Long roleId);

    @Query(
            value = "update {h-schema}user u " +
                    "set role_id = (select id from {h-schema}role where role.name = 'VISUALIZER') " +
                    "where u.id in (select id from {h-schema}user t where t.role_id = :roleId)",
            nativeQuery = true)
    @Modifying
    void assignRoleVisualizerAllUserThatHaveRoleId(@Param("roleId") Long roleId);

    Role getByName(String name);

}
