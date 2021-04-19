package com.tplate.layers.b.business.validators;

import com.tplate.layers.b.business.exceptions.CredentialsNotFoundException;
import com.tplate.layers.b.business.exceptions.UsernameExistException;
import com.tplate.layers.c.persistence.repositories.CredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CredentialsValidator {

    @Autowired
    CredentialsRepository credentialsRepository;

    public void guaranteeNotExistUsername(String username) throws UsernameExistException {
        if (this.credentialsRepository.existsByUsername(username)) {
            log.error("Username exists. {}", username);
            throw new UsernameExistException();
        }
    }

    public void guaranteeExistById(Long idCredentials) throws CredentialsNotFoundException {
        if (!this.credentialsRepository.existsById(idCredentials)) {
            log.warn("Id Credentials not found. {}", idCredentials);
            throw new CredentialsNotFoundException();
        }
    }
}
