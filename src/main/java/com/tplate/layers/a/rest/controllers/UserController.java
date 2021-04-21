package com.tplate.layers.a.rest.controllers;

import com.tplate.layers.a.rest.dtos.ResponseDto;
import com.tplate.layers.a.rest.dtos.SimpleResponseDto;
import com.tplate.layers.a.rest.dtos.user.UserNewDto;
import com.tplate.layers.a.rest.dtos.user.UserUpdateDto;
import com.tplate.layers.a.rest.dtos.user.UserResponseDto;
import com.tplate.layers.b.business.exceptions.*;
import com.tplate.layers.b.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(PathConstant.BASE_PATH + "/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/new-user")
//    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseDto createUser(@RequestBody(required = true) @Valid UserNewDto userDto) throws RoleNotExistException, UsernameExistException, EmailExistException {

        return ResponseDto.builder()
                .message("User created.")
                .details("User was created successfully.")
                .data(this.userService.saveModel(userDto), UserResponseDto.class)
                .build();

    }

    @PutMapping(value = "/{id}")
//    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseDto updateUser(@RequestBody(required = true) @Valid UserUpdateDto userDto, @PathVariable Long id) throws EmailExistException, UserNotExistException, RoleNotExistException, UsernameExistException {
        return ResponseDto.builder()
                .message("User updated.")
                .details("User was updated successfully.")
                .data(this.userService.updateModel(userDto, id), UserResponseDto.class)
                .build();

    }

    @GetMapping(value = "/{id}")
//    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseDto getUserById(@PathVariable Long id) throws UserNotExistException {
        return ResponseDto.builder()
                .message("User data.")
                .details("User data found successfully.")
                .data(this.userService.getModelById(id), UserResponseDto.class)
                .build();
    }

    @GetMapping(value = "")
//    @PreAuthorize("hasAuthority('READ_USERS')")
    public Page findUsers(Pageable pageable) {
//        return ResponseDto.builder()
//                .message("User data.")
//                .details("User data found successfully.")
//                .data(this.userService.getModels(???), UserResponseDto.class)
//                .build();
        return this.userService.findAll(pageable);
    }

    @DeleteMapping(value = "/{id}")
//    @PreAuthorize("hasAuthority('DELETE_USERS')")
    public SimpleResponseDto deleteUser(@PathVariable Long id) throws UserNotExistException {
        this.userService.deleteModelById(id);
        return SimpleResponseDto.builder()
                .message("User removed.")
                .details("The user was removed successfully.")
                .build();
    }


}
