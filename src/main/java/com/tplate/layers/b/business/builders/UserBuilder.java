package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.user.NewUserDto;
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

    @Autowired
    ModelMapper modelMapper;

    public User buildModelByDto(NewUserDto userDto) throws RoleNotFoundException, UsernameExistException, EmailExistException {
        return  User.builder()
                .role(this.roleService.getModelBy(userDto.getRoleId()))
                .contact(this.contactService.buildModelBy(userDto.getContact()))
                .credentials(this.credentialsService.modelFrom(userDto.getCredentials()))
                .build();

    }

    public Object buildDto(User user, Class dtoClass) {
        return this.modelMapper.map(user, dtoClass);
    }
}
