package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.user.UserDto;
import com.tplate.layers.b.business.exceptions.EmailExistException;
import com.tplate.layers.b.business.exceptions.RoleNotFoundException;
import com.tplate.layers.b.business.exceptions.UsernameExistException;
import com.tplate.layers.b.business.services.ContactService;
import com.tplate.layers.b.business.services.CredentialsService;
import com.tplate.layers.b.business.services.RoleService;
import com.tplate.layers.c.persistence.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBuilder {

    @Autowired
    RoleService roleService;

    @Autowired
    ContactService contactService;

    @Autowired
    CredentialsService credentialsService;

    public User buildModelByDto(UserDto userDto) throws RoleNotFoundException, UsernameExistException, EmailExistException {
        return  User.builder()
                .role(this.roleService.getModelById(userDto.getRoleId()))
                .contact(this.contactService.newModelByDTO(userDto.getContact()))
                .credentials(this.credentialsService.newModelByDTO(userDto.getCredentials()))
                .build();

    }
}
