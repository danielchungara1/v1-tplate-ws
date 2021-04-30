package com.tplate.layers.business.services;

// External Dependencies
import com.google.common.collect.ImmutableMap;
import com.tplate.layers.business.shared.LoginModel;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.layers.business.exceptions.ResetCodeExpiredException;
import com.tplate.layers.business.exceptions.ResetCodeNotFoundException;
import com.tplate.layers.business.exceptions.ResetCodeNotMatchingException;
import com.tplate.layers.business.shared.PasswordRecoveryUtil;
import com.tplate.security.jwt.JwtCustomException;
import com.tplate.security.jwt.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Internal Dependencies
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.tplate.layers.persistence.models.PasswordRecovery;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.persistence.repositories.PasswordRecoveryRepository;
import com.tplate.layers.business.shared.Email;
import com.tplate.layers.business.exceptions.EmailNotFoundException;

import java.util.Date;
import java.util.Random;

@Service
@Log4j2
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordRecoveryRepository passwordRecoveryRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Transactional
    public LoginModel login(LoginDto loginDto) throws AuthenticationException, JwtCustomException {

        // Credentials Validation
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()
                )
        );

        // Generate token
        LoginModel loginModel = LoginModel.builder()
                .token(this.jwtTokenUtil.generateToken(this.userRepository.getByUsername(loginDto.getUsername())))
                .build();
        log.info("User logged OK. {}", loginDto.getUsername());

        //Response
        return loginModel;

    }

    @Transactional
    public void resetPasswordStep1(ResetPasswordStep1Dto dto) throws EmailNotFoundException {

            // Generate Reset Code
            User user = this.userService.getModelByEmail(dto.getEmail());
            PasswordRecovery resetPassword = user.getPasswordRecovery();

            // If not exists reset code then create it.
            if (resetPassword == null) {
                resetPassword = PasswordRecovery.builder()
                        .code(PasswordRecoveryUtil.code())
                        .expirationDate(PasswordRecoveryUtil.expiration())
                        .build();
            } else { // If already exists then update it.
                resetPassword.setCode(PasswordRecoveryUtil.code());
                resetPassword.setExpirationDate(PasswordRecoveryUtil.expiration());
            }

            user.setPasswordRecovery(resetPassword);
            this.userRepository.save(user);

            // Send email
            this.emailService.send(Email.builder()
                    .to(dto.getEmail())
                    .subject("Reset Code for change password.")
                    .data(ImmutableMap.<String, Object>builder()
                            .put("resetCode", resetPassword.getCode())
                            .build()
                    )
                    .build());

            log.info("Email with the reset code was sent successfully. {}", dto.getEmail());

    }

    @Transactional
    public void resetPasswordStep2(ResetPasswordStep2Dto dto) throws EmailNotFoundException, ResetCodeNotFoundException, ResetCodeNotMatchingException, ResetCodeExpiredException {

            // Validate reset code
            User user = this.userService.getModelByEmail(dto.getEmail());
            PasswordRecovery passwordRecovery = user.getPasswordRecovery();

            if (passwordRecovery == null) {
                log.error("Reset code not found. {}", dto.getCode());
                ResetCodeNotFoundException.throwsException(dto.getCode());
            }

            // Validate matching
            if (!passwordRecovery.getCode().equals(dto.getCode())) {
                log.error("Codes not matching {} {}."
                        , passwordRecovery.getCode(), dto.getCode());
                ResetCodeNotMatchingException.throwsException(dto.getCode());
            }

            // Validate expiration
            if (!(new Date(System.currentTimeMillis()).before(passwordRecovery.getExpirationDate()))) {
                log.error("Code has expired {}.", passwordRecovery.getCode());
                ResetCodeExpiredException.throwsException(passwordRecovery.getCode());
            }

            // Save new password
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);
            log.info("The new password has been updated. User {}", user.getUsername());

    }

}
