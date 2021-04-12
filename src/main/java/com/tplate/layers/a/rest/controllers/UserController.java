package com.tplate.layers.a.rest.controllers;

import com.tplate.layers.a.rest.dtos.user.UserProfileDto;
import com.tplate.layers.a.rest.dtos.user.NewUserDto;
import com.tplate.layers.b.business.exceptions.restexceptions.UserDtoException;
import com.tplate.layers.b.business.services.UserService;
import com.tplate.layers.b.business.exceptions.restexceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.BASE_PATH + "/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/new-user")
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseEntity createUser(@RequestBody(required=true) NewUserDto newUserDto) throws UserDtoException, UserExistException {
        return this.userService.createUser(newUserDto);
    }

    @GetMapping(value = "/{id}/profile")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseEntity getProfile(@PathVariable Long id){
        return this.userService.getProfile(id);
    }

    @PutMapping(value = "/{id}/profile")
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    public ResponseEntity editProfile(@RequestBody(required=true) UserProfileDto userProfileDto,
                                @PathVariable Long id){
        return this.userService.editProfile(userProfileDto, id);
    }

}
