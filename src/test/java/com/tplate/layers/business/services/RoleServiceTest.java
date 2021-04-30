package com.tplate.layers.business.services;

import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.persistence.models.Permission;
import com.tplate.layers.persistence.models.Role;
import com.tplate.layers.persistence.repositories.PermissionRepository;
import com.tplate.layers.persistence.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@Slf4j
class RoleServiceTest extends BasePostgreContainerTests {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    Role roleExpected;
    List<Permission> permissionsExpected;

    final Long NOT_EXIST_ID = -1L;

    @BeforeEach
    public void beforeEach() throws Exception {

        this.permissionsExpected = this.permissionRepository.saveAll(
                Arrays.asList(
                        Permission.builder().name("Permission Name Test. " + UUID.randomUUID()).description("Permission Description Test. " + UUID.randomUUID()).build(),
                        Permission.builder().name("Permission Name Test. " + UUID.randomUUID()).description("Permission Description Test. " + UUID.randomUUID()).build(),
                        Permission.builder().name("Permission Name Test. " + UUID.randomUUID()).description("Permission Description Test. " + UUID.randomUUID()).build(),
                        Permission.builder().name("Permission Name Test. " + UUID.randomUUID()).description("Permission Description Test. " + UUID.randomUUID()).build()
                )
        );

        this.roleExpected = this.roleRepository.save(
                Role.builder()
                        .name("Role Name Test. " + UUID.randomUUID())
                        .description("Role Description Test. " + UUID.randomUUID())
                        .permissions(permissionsExpected)
                        .build()
        );

        if (roleExpected.getId() == null) {
            throw new Exception("Unable to create expected role." + this.getClass().getCanonicalName());
        }
    }

    @AfterEach
    public void afterAll() {
        this.roleRepository.delete(this.roleExpected);
        this.permissionRepository.deleteAll(this.permissionsExpected);
    }

    @Test
    @Transactional
    void getModelById_withExistingId() throws RoleNotExistException {

        Role role = this.roleService.getModelById(this.roleExpected.getId());

        assertThat(role).isNotNull();
        assertThat(role.getId()).isEqualTo(roleExpected.getId());
        assertThat(role.getName()).isEqualTo(roleExpected.getName());
        assertThat(role.getDescription()).isEqualTo(roleExpected.getDescription());

        assertThat(role.getPermissions().toString())
                .isEqualTo(roleExpected.getPermissions().toString());

        log.info(role.getPermissions().toString());
        log.info(roleExpected.getPermissions().toString());

    }

    @Test
    void getModelById_withoutExistingId() {
        assertThatThrownBy(() -> this.roleService.getModelById(NOT_EXIST_ID))
                .isInstanceOf(RoleNotExistException.class);
    }
}