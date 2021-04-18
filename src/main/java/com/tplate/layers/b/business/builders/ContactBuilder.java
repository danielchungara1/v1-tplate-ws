package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.user.ContactDto;
import com.tplate.layers.c.persistence.models.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactBuilder {

    public Contact buildModelBy(ContactDto contactDtoIn)  {

        return  Contact.builder()
                .name(contactDtoIn.getName())
                .lastname(contactDtoIn.getLastname())
                .phone(contactDtoIn.getPhone())
                .email(contactDtoIn.getEmail())
                .build();

    }

}
