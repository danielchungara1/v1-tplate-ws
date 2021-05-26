package com.tplate.layers.business.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tplate.ContainersTests;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.layers.business.exceptions.auth.*;
import com.tplate.layers.business.services.authService.AuthService;
import com.tplate.layers.business.services.authService.LoginModel;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.layers.persistence.repositories.UserRepository;
import com.tplate.config.security.jwt.JwtCustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class AuthServiceTest extends ContainersTests {

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


    @Test
    void resetPasswordStep1_withNonExistingEmail() {
        assertThatThrownBy(() ->
                this.authService.resetPasswordStep1(
                        ResetPasswordStep1Dto.builder()
                                .email(UUID.randomUUID().toString())
                                .build()
                )
        ).isInstanceOf(EmailNotFoundException.class);
    }

    @Test
    @Transactional
    void resetPasswordStep1_forNewUserMustSaveResetCode() throws EmailNotFoundException, EmailSenderException {
        // Precondition
        assertThat(newUser.getPasswordRecovery()).isNull();

        this.authService.resetPasswordStep1(
                ResetPasswordStep1Dto.builder()
                        .email(newUser.getEmail())
                        .build()
        );
        User userUpdated = this.userRepository.getByUsername(newUser.getUsername());

        assertThat(userUpdated.getPasswordRecovery()).isNotNull();
        assertThat(userUpdated.getPasswordRecovery().getCode()).isNotBlank();
        assertThat(userUpdated.getPasswordRecovery().getExpirationDate()).isNotNull();
    }

    @Test
    void resetPasswordStep2_withNonExistingEmail() {

        assertThatThrownBy(() ->
                this.authService.resetPasswordStep2(
                        ResetPasswordStep2Dto.builder()
                                .email(UUID.randomUUID().toString())
                                .build()
                )
        ).isInstanceOf(EmailNotFoundException.class);

    }

    @Test
    void resetPasswordStep2_withNotCodeGeneratedYet() {

        assertThatThrownBy(() ->
                this.authService.resetPasswordStep2(
                        ResetPasswordStep2Dto.builder()
                                .email(newUser.getEmail())
                                .build()
                )
        ).isInstanceOf(ResetCodeNotFoundException.class);

    }
    @Test
    void resetPasswordStep2_withNonMatchingCode() throws EmailNotFoundException, EmailSenderException {

        // Precondition (Generate reset code)
        this.authService.resetPasswordStep1(
                ResetPasswordStep1Dto.builder()
                        .email(newUser.getEmail())
                        .build()
        );

        assertThatThrownBy(() ->
                this.authService.resetPasswordStep2(
                        ResetPasswordStep2Dto.builder()
                                .email(newUser.getEmail())
                                .code(UUID.randomUUID().toString())
                                .build()
                )
        ).isInstanceOf(ResetCodeNotMatchingException.class);

    }

    @Test
    @Transactional
    void resetPasswordStep2_withResetCodeExpired() throws EmailNotFoundException, EmailSenderException {

        // Precondition 1 (Generate reset code)
        this.authService.resetPasswordStep1(
                ResetPasswordStep1Dto.builder()
                        .email(newUser.getEmail())
                        .build()
        );

        // Precondition 2 (Change expiration date)
        newUser = this.userRepository.getByUsername(newUser.getUsername());
        newUser.getPasswordRecovery().setExpirationDate(
                Date.from(Instant.now().minus(1, ChronoUnit.DAYS))
        );
        newUser = this.userRepository.save(newUser);

        assertThatThrownBy(() ->
                this.authService.resetPasswordStep2(
                        ResetPasswordStep2Dto.builder()
                                .email(newUser.getEmail())
                                .code(newUser.getPasswordRecovery().getCode())
                                .build()
                )
        ).isInstanceOf(ResetCodeExpiredException.class);

    }
}
