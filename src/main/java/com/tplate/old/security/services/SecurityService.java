package com.tplate.old.security.services;

// External Dependencies
import com.google.common.collect.ImmutableMap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Internal Dependencies
import com.tplate.layers.persistence.repositories.RoleRepository;
import com.tplate.old.security.email.EmailService;
import com.tplate.old.security.jwt.JwtTokenUtil;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.tplate.layers.admission.dtos.ResponseDto;
import com.tplate.layers.business.builders.UserBuilder;
import com.tplate.layers.persistence.models.PasswordRecovery;
import com.tplate.old.security.dtos.LoginDto;
import com.tplate.old.security.dtos.ResetPasswordStep1Dto;
import com.tplate.old.security.dtos.TokenDto;
import com.tplate.layers.persistence.repositories.PasswordRecoveryRepository;
import com.tplate.old.security.email.Email;
import com.tplate.old.security.exceptions.EmailNotFoundException;

@Service
@Log4j2
public class SecurityService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    EmailService emailService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordRecoveryRepository passwordRecoveryRepository;

    @Autowired
    ResetPasswordService resetPasswordService;

    @Autowired
    UserBuilder userBuilder;

    @Transactional
    public ResponseEntity loguear(LoginDto loginDto) {

        // Credentials Validation
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()
                )
        );

        // Generate token
        String token = this.jwtTokenUtil.generateToken(this.userRepository.findByUsername(loginDto.getUsername()).get());
        User user = this.userRepository.findByUsername(loginDto.getUsername()).get();
        user.setToken(token);
        log.info("User logged OK. {}", loginDto.getUsername());

        //Response
        return ResponseEntity.ok(
                ResponseDto
                        .builder()
                        .message("User logged.")
                        .details("User logged successfully.")
                        .data(user, TokenDto.class)
                        .build());
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
                        .code(this.resetPasswordService.code())
                        .expirationDate(this.resetPasswordService.expiration())
                        .build();
            } else { // If already exists then only I update it.
                resetPassword.setCode(this.resetPasswordService.code());
                resetPassword.setExpirationDate(this.resetPasswordService.expiration());
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
//
//    @Transactional
//    public ResponseEntity resetPasswordStep2(ResetPasswordStep2Dto resetPasswordDto) {
//        try {
//            // Dto Validation
//            resetPasswordDto.validate();
//            String email = resetPasswordDto.getEmail();
//
//            // Validate existence
//            if (!this.userRepository.existsByUsername(email)) {
//                log.error("Email not exist. {}", email);
//                throw new EmailNotFoundException();
//            }
//
//            // Validate reset code
//            ResetPassword resetPassword = this.resetCodeRepository
//                    .findByUser(this.userRepository.findByUsername(email).get())
//                    .orElse(null);
//            if (resetPassword == null) {
//                log.error("Reset code not found for email {}", email);
//                throw new ResetCodeNotFoundException();
//            }
//
//            // Validate matching
//            if (!resetPassword.getCode().equals(resetPasswordDto.getCode())) {
//                log.error("Codes not matching {} {}."
//                        , resetPassword.getCode(), resetPasswordDto.getCode());
//                throw new ResetCodeNotMatchingException();
//            }
//
//            // Validate expiration
//            if (!(new Date(System.currentTimeMillis()).before(resetPassword.getExpiration()))) {
//                log.error("Code has expired {}.", resetPassword.getCode());
//                throw new ResetCodeExpiredException();
//            }
//
//            // Save new password
//            User user = this.userRepository.findByUsername(resetPasswordDto.getEmail()).get();
//            user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
//            userRepository.save(user);
//            log.info("The new password has been saved. User {}", user.getUsername());
//
//            return ResponseBuilder.builder()
//                    .ok()
//                    .message("The password has been changed.")
//                    .build();
//        } catch ( FormValidatorException | ResetCodeNotFoundException
//                | EmailNotFoundException | ResetCodeNotMatchingException
//                | ResetCodeExpiredException e ) {
//            return ResponseBuilder.buildConflict(e.getMessage());
//
//        } catch (Exception e) {
//            log.error("Something was wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
//            return ResponseBuilder.buildSomethingWrong();
//        }
//    }


}
