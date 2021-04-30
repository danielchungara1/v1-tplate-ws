package com.tplate.layers.business.services;

import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.access.dtos.user.UserUpdateDto;
import com.tplate.layers.business.exceptions.*;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserServiceUpdateTest extends BasePostgreContainerTests {

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

    // Existing User 1
    User existingUser1;
    final String INITIAL_PASSWORD1 = UUID.randomUUID().toString();

    // Existing User 2
    User existingUser2;
    final String INITIAL_PASSWORD2 = UUID.randomUUID().toString();

    @BeforeEach
    public void beforeEach() throws Exception {
        // Create User 1
        this.existingUser1 = this.userRepository.save(
                User.builder()
                        .username(UUID.randomUUID().toString())
                        .password(this.passwordEncoder.encode(INITIAL_PASSWORD1))
                        .name(UUID.randomUUID().toString())
                        .lastname(UUID.randomUUID().toString())
                        .email(UUID.randomUUID().toString())
                        .phone(UUID.randomUUID().toString())
                        .role(this.roleRepository.getOneById(USER_ADMIN))
                        .build()
        );
        if (this.existingUser1.getId() == null) {
            throw new Exception("Failed to save user.");
        }

        // Create User 2
        this.existingUser2 = this.userRepository.save(
                User.builder()
                        .username(UUID.randomUUID().toString())
                        .password(this.passwordEncoder.encode(INITIAL_PASSWORD2))
                        .name(UUID.randomUUID().toString())
                        .lastname(UUID.randomUUID().toString())
                        .email(UUID.randomUUID().toString())
                        .phone(UUID.randomUUID().toString())
                        .role(this.roleRepository.getOneById(USER_ADMIN))
                        .build()
        );
        if (this.existingUser2.getId() == null) {
            throw new Exception("Failed to save user.");
        }
    }

    @AfterEach
    public void afterAll() {
        this.userRepository.delete(existingUser1);
        this.userRepository.delete(existingUser2);
    }

    @Test
    void updateModel_withExistingUserValid() throws EmailExistException, UsernameExistException, RoleNotExistException, UserNotExistException {

        UserUpdateDto userDto = new UserUpdateDto();
        userDto.setUsername(UUID.randomUUID().toString());
        userDto.setPassword(UUID.randomUUID().toString());
        userDto.setName(UUID.randomUUID().toString());
        userDto.setLastname(UUID.randomUUID().toString());
        userDto.setEmail(UUID.randomUUID().toString());
        userDto.setPhone(UUID.randomUUID().toString());
        userDto.setRoleId(ROLE_ADMIN);

        User userUpdated = this.userService.updateModel(userDto, existingUser1.getId());

        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getId()).isEqualTo(existingUser1.getId());
        assertThat(userUpdated.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(this.passwordEncoder.matches(INITIAL_PASSWORD1, userUpdated.getPassword())).isFalse();
        assertThat(userUpdated.getName()).isEqualTo(userDto.getName());
        assertThat(userUpdated.getLastname()).isEqualTo(userDto.getLastname());
        assertThat(userUpdated.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(userUpdated.getPhone()).isEqualTo(userDto.getPhone());
        assertThat(userUpdated.getRole()).isNotNull();
        assertThat(userUpdated.getRole().getId()).isEqualTo(userDto.getRoleId());

    }

    @Test
    void updateModel_withExistingEmail() {
        // Email(User 1) update to Email(User 2)
        UserUpdateDto dtoUser1 = new UserUpdateDto();
        dtoUser1.setUsername(this.existingUser1.getUsername());
        dtoUser1.setPassword(this.INITIAL_PASSWORD1);
        dtoUser1.setName(this.existingUser1.getName());
        dtoUser1.setLastname(this.existingUser1.getLastname());

        dtoUser1.setEmail(this.existingUser2.getEmail());

        dtoUser1.setPhone(this.existingUser1.getPhone());
        dtoUser1.setRoleId(ROLE_ADMIN);

        assertThatThrownBy(() -> this.userService.updateModel(dtoUser1, existingUser1.getId()))
                .isInstanceOf(EmailExistException.class);

    }

    @Test
    void updateModel_withExistingUsername() {
        // Username(User 1) update to Username(User 2)
        UserUpdateDto dtoUser1 = new UserUpdateDto();

        dtoUser1.setUsername(this.existingUser2.getUsername());

        dtoUser1.setPassword(this.INITIAL_PASSWORD1);
        dtoUser1.setName(this.existingUser1.getName());
        dtoUser1.setLastname(this.existingUser1.getLastname());
        dtoUser1.setEmail(this.existingUser1.getEmail());
        dtoUser1.setPhone(this.existingUser1.getPhone());
        dtoUser1.setRoleId(ROLE_ADMIN);

        assertThatThrownBy(() -> this.userService.updateModel(dtoUser1, existingUser1.getId()))
                .isInstanceOf(UsernameExistException.class);

    }

    @Test
    void updateModel_withNonExistingRole() {

        UserUpdateDto dtoUser1 = new UserUpdateDto();
        dtoUser1.setUsername(this.existingUser1.getUsername());
        dtoUser1.setPassword(this.INITIAL_PASSWORD1);
        dtoUser1.setName(this.existingUser1.getName());
        dtoUser1.setLastname(this.existingUser1.getLastname());
        dtoUser1.setEmail(this.existingUser1.getEmail());
        dtoUser1.setPhone(this.existingUser1.getPhone());

        dtoUser1.setRoleId(NON_EXISTING_ID);

        assertThatThrownBy(() -> this.userService.updateModel(dtoUser1, existingUser1.getId()))
                .isInstanceOf(RoleNotExistException.class);

    }

    @Test
    void updateModel_withNonExistingUserId() {

        UserUpdateDto dtoUser1 = new UserUpdateDto();
        dtoUser1.setUsername(this.existingUser1.getUsername());
        dtoUser1.setPassword(this.INITIAL_PASSWORD1);
        dtoUser1.setName(this.existingUser1.getName());
        dtoUser1.setLastname(this.existingUser1.getLastname());
        dtoUser1.setEmail(this.existingUser1.getEmail());
        dtoUser1.setPhone(this.existingUser1.getPhone());
        dtoUser1.setRoleId(ROLE_ADMIN);

        assertThatThrownBy(() -> this.userService.updateModel(dtoUser1, NON_EXISTING_ID))
                .isInstanceOf(UserNotExistException.class);

    }

}