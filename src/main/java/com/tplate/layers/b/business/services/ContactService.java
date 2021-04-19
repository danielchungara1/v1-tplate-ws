package com.tplate.layers.b.business.services;

import com.tplate.layers.a.rest.dtos.user.ContactDto;
import com.tplate.layers.b.business.builders.ContactBuilder;
import com.tplate.layers.b.business.exceptions.*;
import com.tplate.layers.b.business.validators.ContactValidator;
import com.tplate.layers.c.persistence.models.Contact;
import com.tplate.layers.c.persistence.repositories.ContactRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Contact newModelByDTO(ContactDto contactDtoIn) throws EmailExistException {

        this.contactValidator.guaranteeNotExistEmail(contactDtoIn.getEmail());

        return this.contactBuilder.buildModelBy(contactDtoIn);

    }

    @Transactional
    public Contact updateModelByDTO(ContactDto contactDto, Long idContact) throws ContactNotFoundException, EmailExistException {


        Contact contact = this.updateEmail(this.getModelById(idContact), contactDto.getEmail());

        contact.setName(contactDto.getName());
        contact.setLastname(contactDto.getLastname());
        contact.setPhone(contactDto.getPhone());

        return this.contactRepository.save(contact);

    }

    @Transactional
    public Contact getModelById(Long idContact) throws ContactNotFoundException {
        this.contactValidator.guaranteeExistById(idContact);
        return this.contactRepository.getOne(idContact);
    }

    @Transactional
    public Contact updateEmail(Contact contact, String email) throws EmailExistException {
        contact.setEmail(email);

        try {
            return this.contactRepository.saveAndFlush(contact);

        }catch (DataIntegrityViolationException e) {
            throw new EmailExistException();

        }
    }

    @Transactional
    public Contact getModelByEmail(String email) throws ContactEmailNotFoundException {
        this.contactValidator.guaranteeExistByEmail(email);
        return this.contactRepository.getContactByEmail(email).get();
    }
}
