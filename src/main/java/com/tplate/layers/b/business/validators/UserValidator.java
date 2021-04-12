package com.tplate.layers.b.business.validators;

import com.tplate.layers.a.rest.dtos.user.NewUserDto;
import com.tplate.layers.b.business.exceptions.restexceptions.UserDtoException;
import com.tplate.layers.c.persistence.UserRepository;
import com.tplate.old.exceptions.FormValidatorException;
import com.tplate.layers.b.business.exceptions.restexceptions.UserExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserValidator {

    @Autowired
    UserRepository userRepository;

    public void validate(NewUserDto dto) throws UserDtoException {
        try {
            dto.validate();

        } catch (FormValidatorException e) {
            log.error("Exception: {}. Message: {}.", e.getClass().getCanonicalName(), e.getMessage());
            throw new UserDtoException(e.getMessage());
        }
    }

    public void validateIfExist(String username) throws UserExistException {
        if (this.userRepository.existsByUsername(username)) {
            log.error("Username exists. {}", username);
            throw new UserExistException();
        }
    }
}
