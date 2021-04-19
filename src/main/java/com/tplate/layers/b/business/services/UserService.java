package com.tplate.layers.b.business.services;

import com.tplate.layers.b.business.builders.UserBuilder;
import com.tplate.layers.b.business.exceptions.*;
import com.tplate.layers.b.business.validators.UserValidator;
import com.tplate.layers.c.persistence.models.User;
import com.tplate.layers.a.rest.dtos.user.UserDto;
import com.tplate.layers.c.persistence.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserBuilder userBuilder;

    @Autowired
    RoleService roleService;

    @Autowired
    ContactService contactService;

    @Autowired
    CredentialsService credentialsService;

    /**
     * Update model from DTO.
     * Strategy: All fields are updated, null fields are permitted if DTO satisfy all validations.
     * @param userDto
     * @param idUser
     * @return ResponseDto
     * @throws RoleNotFoundException
     * @throws UsernameExistException
     * @throws EmailExistException
     */
    @Transactional (rollbackFor = {Exception.class})
    public User updateModelByDto(UserDto userDto, Long idUser) throws EmailExistException, ContactNotFoundException, UserNotFoundException, UsernameExistException, CredentialsNotFoundException, RoleNotFoundException {

        User user = this.getModelById(idUser);

        this.contactService.updateModelByDTO(userDto.getContact(), user.getContact().getId());
        this.credentialsService.updateModelByDTO(userDto.getCredentials(), user.getCredentials().getId());
        this.updateRole(user, userDto.getRoleId());

        return this.getModelById(idUser);

    }

    /**
     * Save model from DTO.
     * @param newUserDto
     * @return ResponseDto
     * @throws RoleNotFoundException
     * @throws UsernameExistException
     * @throws EmailExistException
     */
    @Transactional
    public User saveModelByDto(UserDto newUserDto)
            throws RoleNotFoundException, UsernameExistException, EmailExistException {

        // Build Model
        User newUser = this.userBuilder.buildModelByDto(newUserDto);

        // Transaction
        return this.userRepository.save(newUser);

    }

    @Transactional
    public User getModelById(Long id) throws UserNotFoundException {

        this.userValidator.guaranteeExistById(id);

        return this.userRepository.getOne(id);
    }

    @Transactional
    public User updateRole(User user, Long idRol) throws RoleNotFoundException {
        this.roleService.guaranteeExistById(idRol);
        user.setRole(this.roleService.getModelById(idRol));
        return this.userRepository.save(user);
    }

}
