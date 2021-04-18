package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.user.CredentialsDto;
import com.tplate.layers.c.persistence.models.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialsBuilder {

    @Autowired
    PasswordEncoder passwordEncoder;

    public Credentials buildModel(CredentialsDto credentialsDtoIn) {

        return  Credentials.builder()
                .username(credentialsDtoIn.getUsername())
                .password(this.passwordEncoder.encode(credentialsDtoIn.getPassword()))
                .build();

    }

}
