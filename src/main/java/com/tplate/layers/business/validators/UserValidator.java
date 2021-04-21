package com.tplate.layers.business.validators;

import com.tplate.layers.business.exceptions.EmailExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.business.exceptions.UsernameExistException;
import com.tplate.layers.persistence.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserValidator {

    @Autowired
    UserRepository userRepository;

    public void guaranteeExistById(Long id ) throws UserNotExistException {
        if (!this.userRepository.existsById(id)) {
            log.error("User not exist. {}", id);
            throw new UserNotExistException();
        }
    }

    public void guaranteeNotExistEmail(String email) throws EmailExistException {
        if (this.userRepository.existsByEmail(email)) {
            log.error("Email exist. {}", email);
            throw new EmailExistException();
        }
    }

    public void guaranteeNotExistUsername(String username) {
    }

    public void throwsEmailExistException() throws EmailExistException {
        throw new EmailExistException();
    }

    public void throwsUsernameExistException() throws UsernameExistException {
        throw new UsernameExistException();
    }
}
