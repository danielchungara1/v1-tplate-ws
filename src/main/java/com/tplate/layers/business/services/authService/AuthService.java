package com.tplate.layers.business.services.authService;

import com.tplate.layers.access.dtos.auth.SignUpDto;
import com.tplate.layers.access.dtos.user.UserNewDto;
import com.tplate.layers.business.exceptions.auth.*;
import com.tplate.layers.business.exceptions.role.RoleNotExistException;
import com.tplate.layers.business.exceptions.user.UsernameExistException;
import com.tplate.layers.business.services.UserService;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.config.security.jwt.JwtCustomException;
import com.tplate.config.security.jwt.JwtTokenUtil;
import com.tplate.layers.business.shared.RolesConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.tplate.layers.persistence.models.PasswordRecovery;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.persistence.repositories.PasswordRecoveryRepository;

import java.util.Date;

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
        User user = this.userRepository.getByUsername(loginDto.getUsername());
        LoginModel loginModel = LoginModel.builder()
                .token(this.jwtTokenUtil.generateToken(user))
                .user(user)
                .build();
        log.info("User logged OK. {}", loginDto.getUsername());

        //Response
        return loginModel;

    }

    @Transactional
    public void resetPasswordStep1(ResetPasswordStep1Dto dto) throws EmailNotFoundException, EmailSenderException {

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
        this.emailService.send(EmailResetCode.builder()
                .to(dto.getEmail())
                .data(resetPassword.getCode())
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

    @Transactional(rollbackFor = Exception.class)
    public void signUp(SignUpDto dto) throws UsernameExistException, EmailExistException, RoleNotExistException {

        // Todo: change to builder
        UserNewDto userNewDto = new UserNewDto();
        userNewDto.setUsername(dto.getUsername());
        userNewDto.setPassword(dto.getPassword());
        userNewDto.setEmail(dto.getEmail());
        userNewDto.setRoleId(RolesConfig.ROLE_ADMIN.getId());

        this.userService.saveModel(
                userNewDto
        );

    }

}
