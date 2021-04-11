package com.tplate.users.controllers;

import com.tplate.security.dtos.SingUpDto;
import com.tplate.users.dtos.UserProfileDto;
import com.tplate.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/new-user")
    public ResponseEntity createUser(@RequestBody(required=true) SingUpDto singUpDto){
        return this.userService.signUp(singUpDto);
    }

    @GetMapping(value = "/{id}/profile")
    @PreAuthorize("hasAuthority('VISUALIZAR_PERFIL')")
    public ResponseEntity getProfile(@PathVariable Long id){
        return this.userService.getProfile(id);
    }

    @PutMapping(value = "/{id}/profile")
    @PreAuthorize("hasAuthority('EDITAR_PERFIL')")
    public ResponseEntity editProfile(@RequestBody(required=true) UserProfileDto userProfileDto,
                                @PathVariable Long id){
        return this.userService.editProfile(userProfileDto, id);
    }

}
