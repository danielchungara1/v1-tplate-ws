package com.tplate.layers.b.business.validators;

import com.tplate.layers.b.business.exceptions.UserNotFoundException;
import com.tplate.layers.c.persistence.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserValidator {

    @Autowired
    UserRepository userRepository;

    public void guaranteeExistById(Long id ) throws UserNotFoundException {
        if (!this.userRepository.existsById(id)) {
            log.error("User not exist. {}", id);
            throw new UserNotFoundException();
        }
    }

}
