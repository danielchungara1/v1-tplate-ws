package com.tplate.layers.b.business.validators;

import com.tplate.layers.b.business.exceptions.EmailExistException;
import com.tplate.layers.c.persistence.repositories.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactValidator {

    public void guaranteeNotExistEmail(String email, ContactRepository contactRepository) throws EmailExistException {
        if (contactRepository.existsByEmail(email)) {
            log.warn("Email exists. {}", email);
            throw new EmailExistException();
        }
    }
}
