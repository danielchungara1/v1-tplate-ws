package com.tplate.layers.b.business.validators;

import com.tplate.layers.b.business.exceptions.ContactEmailNotFoundException;
import com.tplate.layers.b.business.exceptions.ContactNotFoundException;
import com.tplate.layers.b.business.exceptions.EmailExistException;
import com.tplate.layers.c.persistence.repositories.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactValidator {

    @Autowired
    ContactRepository contactRepository;


    public void guaranteeNotExistEmail(String email) throws EmailExistException {
        if (contactRepository.existsByEmail(email)) {
            log.warn("Email exists. {}", email);
            throw new EmailExistException();
        }
    }

    public void guaranteeExistById(Long idContact) throws ContactNotFoundException {
        if (!contactRepository.existsById(idContact)) {
            log.warn("Id Contact not found. {}", idContact);
            throw new ContactNotFoundException();
        }
    }

    public void guaranteeExistByEmail(String email) throws ContactEmailNotFoundException {
        if (!contactRepository.existsByEmail(email)) {
            log.warn("Email Contact not found. {}", email);
            throw new ContactEmailNotFoundException();
        }
    }
}
