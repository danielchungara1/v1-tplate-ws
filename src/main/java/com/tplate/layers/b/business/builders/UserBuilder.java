package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.user.NewUserDto;
import com.tplate.layers.b.business.models.User;
import com.tplate.old.security.rol.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserBuilder {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolRepository rolRepository;

    public User buildFrom(NewUserDto dto){
        return  User.builder()
                .username(dto.getUsername())
                .password(this.passwordEncoder.encode(dto.getPassword()))
                .rol(this.rolRepository.findByName("ADMIN").get())
                .build();
    }
}
