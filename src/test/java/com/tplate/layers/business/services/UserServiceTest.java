package com.tplate.layers.business.services;

import com.tplate.ContainersTests;
import com.tplate.layers.access.dtos.user.UserNewDto;
import com.tplate.layers.business.exceptions.auth.EmailExistException;
import com.tplate.layers.business.exceptions.role.RoleNotExistException;
import com.tplate.layers.business.exceptions.user.UserNotExistException;
import com.tplate.layers.business.exceptions.user.UsernameExistException;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import java.util.UUID;

class UserServiceTest extends ContainersTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    Long ROLE_ADMIN = 1L;

    Long USER_ADMIN = 1L;

    Long NON_EXISTING_ID = -1L;

    @Test
    void saveModel_withNewUserValid() throws UsernameExistException, EmailExistException, RoleNotExistException {

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(UUID.randomUUID().toString());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(UUID.randomUUID().toString());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(ROLE_ADMIN);

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
    void saveModel_withExistingUsername() throws UserNotExistException {

        User existingUser = this.userRepository.getOneById(USER_ADMIN);

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(existingUser.getUsername());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(UUID.randomUUID().toString());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(ROLE_ADMIN);

        assertThatThrownBy(() -> this.userService.saveModel(userNewDto))
                .isInstanceOf(UsernameExistException.class);

    }

    @Test
    void saveModel_withExistingEmail() throws UserNotExistException {

        User existingUser = this.userRepository.getOneById(USER_ADMIN);

        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(UUID.randomUUID().toString());
        userNewDto.setPassword(UUID.randomUUID().toString());
        userNewDto.setName(UUID.randomUUID().toString());
        userNewDto.setLastname(UUID.randomUUID().toString());
        userNewDto.setEmail(existingUser.getEmail());
        userNewDto.setPhone(UUID.randomUUID().toString());
        userNewDto.setRoleId(ROLE_ADMIN);

        assertThatThrownBy(() -> this.userService.saveModel(userNewDto))
                .isInstanceOf(EmailExistException.class);

    }

    @Test
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
    void getModelById_withExistingId() throws UserNotExistException {

        User existingUser = this.userRepository.getOneById(USER_ADMIN);

        User userUnderTest = this.userService.getModelById(USER_ADMIN);

        assertThat(userUnderTest).isNotNull();
        assertThat(userUnderTest.getId()).isEqualTo(existingUser.getId());
        assertThat(userUnderTest.getUsername()).isEqualTo(existingUser.getUsername());
        assertThat(userUnderTest.getEmail()).isEqualTo(existingUser.getEmail());

        assertThat(userUnderTest.getRole()).isNotNull();
        assertThat(userUnderTest.getRole().getId()).isEqualTo(existingUser.getRole().getId());
    }

    @Test
    void getModelById_withNonExistingId() {

        assertThatThrownBy(() -> this.userService.getModelById(NON_EXISTING_ID))
                .isInstanceOf(UserNotExistException.class);

    }

    @Test
    void deleteModelById_withExistingId() throws UserNotExistException {

        User userCreated = this.userRepository.save(
                User.builder()
                .username(UUID.randomUUID().toString())
                .password(this.passwordEncoder.encode(UUID.randomUUID().toString()))
                .name(UUID.randomUUID().toString())
                .lastname(UUID.randomUUID().toString())
                .email(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString())
                .role(this.roleRepository.getOneById(ROLE_ADMIN))
                .build()
        );
        this.userService.deleteModelById(userCreated.getId());

        assertThat(this.userRepository.findById(userCreated.getId()).isPresent()).isFalse();
    }

    @Test
    void deleteModelById_withNonExistingId() {
        assertThatThrownBy(() -> this.userService.deleteModelById(NON_EXISTING_ID))
                .isInstanceOf(UserNotExistException.class);
    }


}