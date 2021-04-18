package com.tplate.layers.b.business.services;

import com.tplate.layers.a.rest.dtos.user.CredentialsDto;
import com.tplate.layers.b.business.builders.ContactBuilder;
import com.tplate.layers.b.business.builders.CredentialsBuilder;
import com.tplate.layers.b.business.exceptions.UsernameExistException;
import com.tplate.layers.b.business.validators.CredentialsValidator;
import com.tplate.layers.c.persistence.models.Credentials;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class CredentialsService {

    @Autowired
    ContactBuilder contactBuilder;

    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    CredentialsBuilder credentialsBuilder;

    @Transactional
    public Credentials modelFrom(CredentialsDto credentialsDtoIn) throws UsernameExistException {

        this.credentialsValidator.guaranteeNotExistUsername(credentialsDtoIn.getUsername());

        return this.credentialsBuilder.buildModel(credentialsDtoIn);

    }

}
