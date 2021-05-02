package com.tplate.layers.business.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tplate.PostgreBaseContainerTests;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.business.shared.LoginModel;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.layers.persistence.repositories.UserRepository;
import com.tplate.security.SecurityConstants;
import com.tplate.security.jwt.JwtCustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class AuthServiceTest extends PostgreBaseContainerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    static final Long EXISTING_ROLE = 1L;

    User newUser;
    String rawPassword;
    String hashedPassword;

    @Autowired
    AuthService authService;

    @BeforeEach
    public void beforeEach() {

        rawPassword = UUID.randomUUID().toString();
        hashedPassword = this.passwordEncoder.encode(rawPassword);

        newUser = User.builder()
                .username(UUID.randomUUID().toString())
                .password(hashedPassword)
                .email(UUID.randomUUID().toString())
                .role(this.roleRepository.getOneById(EXISTING_ROLE))
                .build();
        this.userRepository.save(newUser);
    }

    @AfterEach
    public void afterEach() {
        this.userRepository.delete(newUser);
        this.rawPassword = null;
        this.hashedPassword = null;
    }

    @Test
    void login_withUsernameAndPassword() throws JwtCustomException {
        LoginModel loginModel = this.authService.login(
                LoginDto.builder()
                        .username(this.newUser.getUsername())
                        .password(this.rawPassword)
                        .build()
        );
        assertThat(loginModel).isNotNull();

        DecodedJWT decodedToken = JWT.decode(loginModel.getToken());
        assertThat(decodedToken).isNotNull();
        assertThat(decodedToken.getSubject()).isEqualTo(this.newUser.getUsername());

    }
}