package com.tplate.layers.b.business.services;

import com.tplate.layers.a.rest.dtos.user.ContactDto;
import com.tplate.layers.b.business.builders.ContactBuilder;
import com.tplate.layers.b.business.exceptions.EmailExistException;
import com.tplate.layers.b.business.validators.ContactValidator;
import com.tplate.layers.c.persistence.models.Contact;
import com.tplate.layers.c.persistence.repositories.ContactRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ContactService {

    @Autowired
    ContactBuilder contactBuilder;

    @Autowired
    ContactValidator contactValidator;

    @Autowired
    ContactRepository contactRepository;


    @Transactional
    public Contact buildModelBy(ContactDto contactDtoIn) throws EmailExistException {

        this.contactValidator.guaranteeNotExistEmail(contactDtoIn.getEmail(), contactRepository);

        return this.contactBuilder.buildModelBy(contactDtoIn);

    }

}
