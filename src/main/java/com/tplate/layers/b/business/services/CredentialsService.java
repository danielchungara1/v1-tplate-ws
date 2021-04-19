package com.tplate.layers.b.business.services;

import com.tplate.layers.a.rest.dtos.user.CredentialsDto;
import com.tplate.layers.b.business.builders.ContactBuilder;
import com.tplate.layers.b.business.builders.CredentialsBuilder;
import com.tplate.layers.b.business.exceptions.*;
import com.tplate.layers.b.business.validators.CredentialsValidator;
import com.tplate.layers.c.persistence.models.Credentials;
import com.tplate.layers.c.persistence.repositories.CredentialsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class CredentialsService {


    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    CredentialsBuilder credentialsBuilder;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional
    public Credentials newModelByDTO(CredentialsDto credentialsDtoIn) throws UsernameExistException {

        this.credentialsValidator.guaranteeNotExistUsername(credentialsDtoIn.getUsername());

        return this.credentialsBuilder.buildModel(credentialsDtoIn);

    }


    @Transactional
    public Credentials updateModelByDTO(CredentialsDto credentialsDto, Long idCredentials) throws CredentialsNotFoundException, UsernameExistException {


        this.updateUsername(this.getModelById(idCredentials), credentialsDto.getUsername());

        if ( credentialsDto.getPassword() != null ) {
            this.updatePassword(this.getModelById(idCredentials), credentialsDto.getPassword());
        }

        return this.getModelById(idCredentials);
    }

    @Transactional
    public Credentials updateUsername(Credentials credentials, String username) throws UsernameExistException {
        credentials.setUsername(username);

        try {
            return this.credentialsRepository.saveAndFlush(credentials);

        }catch (DataIntegrityViolationException e) {
            throw new UsernameExistException();

        }
    }

    @Transactional
    public Credentials updatePassword(Credentials credentials, String password){
        credentials.setPassword(this.passwordEncoder.encode(password));
        return this.credentialsRepository.save(credentials);
    }


    @Transactional
    public Credentials getModelById(Long id) throws CredentialsNotFoundException {

        this.credentialsValidator.guaranteeExistById(id);

        return this.credentialsRepository.getOne(id);
    }
}
