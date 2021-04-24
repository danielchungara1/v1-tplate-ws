package com.tplate.layers.business.services;

import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.persistence.models.Role;
import com.tplate.layers.persistence.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@Slf4j
class RoleServiceTest extends BasePostgreContainerTests {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    final Long EXIST_ID = 1L;
    final Long NOT_EXIST_ID = -1L;

    @Test
    @Transactional
    void getModelById_withExistingId() throws RoleNotExistException {
        // Check role expected exists.
        Role roleExpected = this.roleRepository
                .findById(EXIST_ID)
                .orElseThrow(RoleNotExistException::new);

        Role role = this.roleService.getModelById(EXIST_ID);

        assertThat(role).isNotNull();
        assertThat(role.getId()).isEqualTo(roleExpected.getId());
        assertThat(role.getName()).isEqualTo(roleExpected.getName());
        assertThat(role.getDescription()).isEqualTo(roleExpected.getDescription());

    }

    @Test
    void getModelById_withoutExistingId() {

        assertThatThrownBy(() -> this.roleService.getModelById(NOT_EXIST_ID))
                .isInstanceOf(RoleNotExistException.class);
    }
}