package com.tplate.layers.business.services;

import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.persistence.models.Role;
import com.tplate.layers.persistence.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@Slf4j
@Testcontainers
@SpringBootTest
class RoleServiceTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    RoleService service;

    @Autowired
    RoleRepository roleRepository;

    Role roleExpected;

    final Long EXIST_ID = 1L;

    @BeforeEach
    public void beforeEach() throws RoleNotExistException {
        // Check role expected exists. // Retrieve role expected
        this.roleExpected = this.roleRepository
                .findById(EXIST_ID)
                .orElseThrow(RoleNotExistException::new);
    }

    @Test
    @Transactional
    void getModelById_withExistingId() throws RoleNotExistException {

        Role role = this.service.getModelById(EXIST_ID);

        assertThat(role).isNotNull();

        assertThat(role.getId()).isEqualTo(roleExpected.getId());
        assertThat(role.getName()).isEqualTo(roleExpected.getName());
        assertThat(role.getDescription()).isEqualTo(roleExpected.getDescription());

    }
}