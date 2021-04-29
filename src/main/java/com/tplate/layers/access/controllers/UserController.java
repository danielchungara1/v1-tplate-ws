package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.user.*;
import com.tplate.layers.business.exceptions.EmailExistException;
import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.business.exceptions.UsernameExistException;
import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.business.services.UserService;
import com.tplate.layers.access.shared.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(Paths.USER)
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/new-user")
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseDto createUser(@RequestBody(required = true) @Valid UserNewDto userDto) throws RoleNotExistException, UsernameExistException, EmailExistException {

        return ResponseDto.builder()
                .message("User created.")
                .details("User was created successfully.")
                .data(this.userService.saveModel(userDto), UserResponseDto.class)
                .build();

    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseDto updateUser(@RequestBody(required = true) @Valid UserUpdateDto userDto, @PathVariable Long id) throws EmailExistException, UserNotExistException, RoleNotExistException, UsernameExistException {
        return ResponseDto.builder()
                .message("User updated.")
                .details("User was updated successfully.")
                .data(this.userService.updateModel(userDto, id), UserResponseDto.class)
                .build();

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseDto getUserById(@PathVariable Long id) throws UserNotExistException {
        return ResponseDto.builder()
                .message("User data.")
                .details("User data found successfully.")
                .data(this.userService.getModelById(id), UserResponseDto.class)
                .build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    public ResponseSimpleDto deleteUser(@PathVariable Long id) throws UserNotExistException {
        this.userService.deleteModelById(id);
        return ResponseSimpleDto.builder()
                .message("User removed.")
                .details("The user was removed successfully.")
                .build();
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('READ_USERS')")
    @Transactional
    public ResponseDto findUsers(Pageable pageable) {
        return ResponseDto.builder()
                .message("User data.")
                .details("User data found successfully.")
                .data(this.userService.findAll(pageable), UserPageDto.class)
                .build();
    }

}
