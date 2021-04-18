package com.tplate.old.security.jwt;

import com.tplate.layers.c.persistence.models.User;
import com.tplate.layers.c.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByCredentials_Username(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );

        return new JwtUserDetailsImpl(user);
    }

}