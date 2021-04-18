package com.tplate.layers.a.rest.controllers;

import com.tplate.layers.a.rest.controllers.shared.PathConstant;
import com.tplate.layers.c.persistence.models.Role;
import com.tplate.layers.c.persistence.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstant.BASE_PATH + "/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "")
    public ResponseEntity getRoles() {
        List<Role> roles = this.roleRepository.findAll(); // TODO: Create service
        return new ResponseEntity(roles, HttpStatus.OK);
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
