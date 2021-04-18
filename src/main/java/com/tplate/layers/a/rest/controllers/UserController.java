package com.tplate.layers.a.rest.controllers;

import com.tplate.layers.a.rest.controllers.shared.PathConstant;
import com.tplate.layers.a.rest.dtos.ResponseDto;
import com.tplate.layers.a.rest.dtos.user.NewUserDto;
import com.tplate.layers.b.business.exceptions.EmailExistException;
import com.tplate.layers.b.business.exceptions.RoleNotFoundException;
import com.tplate.layers.b.business.exceptions.UsernameExistException;
import com.tplate.layers.b.business.services.UserService;
import com.tplate.layers.c.persistence.models.User;
import com.tplate.layers.c.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(PathConstant.BASE_PATH + "/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/new-user")
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public ResponseDto createUser(@RequestBody(required=true) @Valid NewUserDto newUserDto) throws  RoleNotFoundException, UsernameExistException, EmailExistException {
        return this.userService.saveModelByDto(newUserDto);
    }

    @GetMapping(value = "")
    public ResponseEntity getUsers() {
        List<User> users = this.userService.getAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

//    @GetMapping(value = "/{id}/profile")
//    @PreAuthorize("hasAuthority('READ_USERS')")
//    public ResponseEntity getProfile(@PathVariable Long id) throws UserNotFoundException {
//        return this.userService.getProfile(id);
//    }
//
//    @PutMapping(value = "/{id}/profile")
//    @PreAuthorize("hasAuthority('UPDATE_USERS')")
//    public ResponseEntity editProfile(@RequestBody(required=true) @Valid UserProfileDto userProfileDto,
//                                @PathVariable Long id) throws UserExistException {
//        return this.userService.editProfile(userProfileDto, id);
//    }

}
