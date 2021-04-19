package com.tplate.layers.a.rest.controllers;

import com.tplate.layers.a.rest.controllers.shared.PathConstant;
import com.tplate.layers.a.rest.dtos.ResponseDto;
import com.tplate.layers.a.rest.dtos.user.UserDto;
import com.tplate.layers.a.rest.dtos.user.UserResponseDto;
import com.tplate.layers.b.business.exceptions.*;
import com.tplate.layers.b.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(PathConstant.BASE_PATH + "/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/new-user")
//    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseDto createUser(@RequestBody(required = true) @Valid UserDto userDto) throws RoleNotFoundException, UsernameExistException, EmailExistException, PasswordRequiredException {

        // Password required
        if (userDto.getCredentials().getPassword() == null) {
            throw new PasswordRequiredException();
        }

        return ResponseDto.builder()
                .message("User Registered.")
                .details("User was registered successfully.")
                .data(this.userService.saveModelByDto(userDto), UserResponseDto.class)
                .build();

    }

    @PutMapping(value = "/{id}")
//    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseDto updateUser(@RequestBody(required = true) @Valid UserDto userDto, @PathVariable Long id) throws EmailExistException, ContactNotFoundException, UserNotFoundException, RoleNotFoundException, UsernameExistException, CredentialsNotFoundException {
        return ResponseDto.builder()
                .message("User updated.")
                .details("User was updated successfully.")
                .data(this.userService.updateModelByDto(userDto, id), UserResponseDto.class)
                .build();

    }

    @GetMapping(value = "/{id}")
//    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseDto getProfile(@PathVariable Long id) throws UserNotFoundException {
        return ResponseDto.builder()
                .message("User found")
                .details("User found successfully")
                .data(this.userService.getModelById(id), UserResponseDto.class)
                .build();
    }



}
