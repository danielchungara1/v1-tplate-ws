package com.tplate.layers.access.controllers;

import com.tplate.ContainersTests;
import com.tplate.layers.access.dtos.user.UserResponseDto;
import com.tplate.layers.business.exceptions.user.UserNotExistException;
import com.tplate.layers.business.services.UserService;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class UserControllerTest extends ContainersTests {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Autowired
    UserRepository userRepository;

    final Long EXISTING_USER = 1L;

    @Test
    @Transactional
    void getUserById_withExistingUser() throws UserNotExistException {

        User existingUser = this.userRepository.getOneById(EXISTING_USER);

        Mockito.when(this.userService.getModelById(Mockito.any()))
                .thenReturn(existingUser);


        UserResponseDto userResponseDto = (UserResponseDto) userController.getUserById(EXISTING_USER).getData();

        assertThat(userResponseDto.getId()).isEqualTo(existingUser.getId());
        assertThat(userResponseDto.getUsername()).isEqualTo(existingUser.getUsername());
        assertThat(userResponseDto.getEmail()).isEqualTo(existingUser.getEmail());

    }
}