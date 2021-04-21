package com.tplate.layers.business.builders;

import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.business.services.RoleService;
import com.tplate.layers.admission.dtos.user.UserUpdateDto;
import com.tplate.layers.business.exceptions.EmailExistException;
import com.tplate.layers.business.exceptions.UsernameExistException;
import com.tplate.layers.persistence.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserBuilder {

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User buildModelByDto(UserUpdateDto userDto) throws RoleNotExistException, UsernameExistException, EmailExistException {
        return  User.builder()
                .role(this.roleService.getModelById(userDto.getRoleId()))

                .email(userDto.getEmail())
                .lastname(userDto.getLastname())
                .name(userDto.getName())
                .phone(userDto.getPhone())


                .build();

    }
}
