package com.tplate.layers.business.services;

import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.access.dtos.user.UserNewDto;
import com.tplate.layers.access.dtos.user.UserUpdateDto;
import com.tplate.layers.business.exceptions.EmailExistException;
import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.business.exceptions.UsernameExistException;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class UserServiceTest extends BasePostgreContainerTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    Long ROL_ADMIN = 1L;

    Long USER_ADMIN = 1L;

    Long NON_EXISTING_ID = -1L;

    @Test
    @Transactional
    void saveModel_withNewUserValid() throws UsernameExistException, EmailExistException, RoleNotExistException {

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(UUID.randomUUID().toString());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(UUID.randomUUID().toString());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(ROL_ADMIN);

        User userSaved = userService.saveModel(userNewDto);

        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getId()).isNotNull();
        assertThat(userSaved.getUsername()).isEqualTo(userNewDto.getUsername());
        assertThat(this.passwordEncoder.matches(userNewDto.getPassword(), userSaved.getPassword())).isTrue();
        assertThat(userSaved.getName()).isEqualTo(userNewDto.getName());
        assertThat(userSaved.getLastname()).isEqualTo(userNewDto.getLastname());
        assertThat(userSaved.getEmail()).isEqualTo(userNewDto.getEmail());
        assertThat(userSaved.getPhone()).isEqualTo(userNewDto.getPhone());
        assertThat(userSaved.getRole()).isNotNull();
        assertThat(userSaved.getRole().getId()).isEqualTo(userNewDto.getRoleId());

    }

    @Test
    @Transactional
    void saveModel_withExistingUsername() {

        User existingUser = this.userRepository.getOne(USER_ADMIN);

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(existingUser.getUsername());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(UUID.randomUUID().toString());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(ROL_ADMIN);

        assertThatThrownBy(() -> this.userService.saveModel(userNewDto))
                .isInstanceOf(UsernameExistException.class);

    }

    @Test
    @Transactional
    void saveModel_withExistingEmail() {

        User existingUser = this.userRepository.getOne(USER_ADMIN);

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(UUID.randomUUID().toString());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(existingUser.getEmail());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(ROL_ADMIN);

        assertThatThrownBy(() -> this.userService.saveModel(userNewDto))
                .isInstanceOf(EmailExistException.class);

    }

    @Test
    @Transactional
    void saveModel_withNonExistingRol() {

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(UUID.randomUUID().toString());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(UUID.randomUUID().toString());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(NON_EXISTING_ID);

        assertThatThrownBy(() -> this.userService.saveModel(userNewDto))
                .isInstanceOf(RoleNotExistException.class);

    }

    @Test
    void deleteModelById() {
    }

    @Test
    void getModelById() {
    }

    @Test
    void findAll() {
    }
}