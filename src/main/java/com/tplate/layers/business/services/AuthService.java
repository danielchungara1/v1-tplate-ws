package com.tplate.layers.business.services;

// External Dependencies
import com.google.common.collect.ImmutableMap;
import com.tplate.layers.persistence.models.LoginModel;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.layers.business.exceptions.ResetCodeExpiredException;
import com.tplate.layers.business.exceptions.ResetCodeNotFoundException;
import com.tplate.layers.business.exceptions.ResetCodeNotMatchingException;
import com.tplate.security.jwt.JwtTokenUtil;
import com.tplate.security.jwt.JwtService;
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
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.tplate.layers.persistence.models.PasswordRecovery;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.persistence.repositories.PasswordRecoveryRepository;
import com.tplate.layers.persistence.models.Email;
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
    JwtService jwtService;

    @Autowired
    EmailService emailService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordRecoveryRepository passwordRecoveryRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Random rand = new Random();

    private final Integer EXPIRATION_MIN = 5;

    @Transactional
    public LoginModel login(LoginDto loginDto) throws AuthenticationException {

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
    public ResponseEntity resetPasswordStep1(ResetPasswordStep1Dto resetPasswordDto) throws EmailNotFoundException {

//        try {

            // Dto Validation
            String email = resetPasswordDto.getEmail();

            // Existence Validation
            if (!this.userRepository.existsByEmail(email)) {
                log.error("Email not found. {}", email);
                throw new EmailNotFoundException();
            }

            // Generate Reset Code
            User user = this.userRepository.getByEmail(email);
            PasswordRecovery resetPassword = user.getPasswordRecovery();

            // If not exists reset code then I create it.
            if (resetPassword == null) {
                resetPassword = PasswordRecovery.builder()
                        .code(this.code())
                        .expirationDate(this.expiration())
                        .build();
            } else { // If already exists then only I update it.
                resetPassword.setCode(this.code());
                resetPassword.setExpirationDate(this.expiration());
            }

            user.setPasswordRecovery(resetPassword);
            this.userRepository.save(user);

            // Send email
            this.emailService.send(Email.builder()
                    .to(email)
                    .subject("Reset Code for change password.")
                    .data(ImmutableMap.<String, Object>builder()
                            .put("resetCode", resetPassword.getCode())
                            .build()
                    )
                    .build());

            log.info("Email with the reset code was sent successfully. {}", email);

            return new ResponseEntity("Email was sent successfully.", HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity resetPasswordStep2(ResetPasswordStep2Dto resetPasswordDto) throws EmailNotFoundException, ResetCodeNotFoundException, ResetCodeNotMatchingException, ResetCodeExpiredException {

            // Dto Validation
            String email = resetPasswordDto.getEmail();

            // Validate existence
            if (!this.userRepository.existsByEmail(email)) {
                log.error("Email not exist. {}", email);
                throw new EmailNotFoundException();
            }

            // Validate reset code
            User user = this.userRepository.getByEmail(email);
            PasswordRecovery resetPassword = user.getPasswordRecovery();

            if (resetPassword == null) {
                log.error("Reset code not found for email {}", email);
                throw new ResetCodeNotFoundException();
            }

            // Validate matching
            if (!resetPassword.getCode().equals(resetPasswordDto.getCode())) {
                log.error("Codes not matching {} {}."
                        , resetPassword.getCode(), resetPasswordDto.getCode());
                throw new ResetCodeNotMatchingException();
            }

            // Validate expiration
            if (!(new Date(System.currentTimeMillis()).before(resetPassword.getExpirationDate()))) {
                log.error("Code has expired {}.", resetPassword.getCode());
                throw new ResetCodeExpiredException();
            }

            // Save new password
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
            userRepository.save(user);
            log.info("The new password has been saved. User {}", user.getUsername());

            return new ResponseEntity("The password has been changed.", HttpStatus.OK);

    }

    public String code (){
        return String.format("%04d", this.rand.nextInt(10000));
    }

    public Date expiration(){
        return  new Date(System.currentTimeMillis() + (this.EXPIRATION_MIN * 60 * 1000));
    }



}
