package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.LoginResponseDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.layers.business.exceptions.*;
import com.tplate.layers.business.services.authService.AuthService;
import com.tplate.layers.access.shared.Endpoints;
import com.tplate.config.security.jwt.JwtCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    AuthService authService;

    // Login
    @PostMapping(Endpoints.AUTH_LOGIN)
    public ResponseDto login(@RequestBody @Valid LoginDto loginDto) throws AuthenticationException, JwtCustomException {
        return ResponseDto.builder()
                .message("User logged in.")
                .details("User logged successfully.")
                .data(this.authService.login(loginDto), LoginResponseDto.class)
                .build();
    }

    // Reset Password Step 1
    @PostMapping(Endpoints.AUTH_RESET_CODE)
    public ResponseSimpleDto resetPassword(@RequestBody @Valid ResetPasswordStep1Dto resetPasswordDto)
            throws EmailNotFoundException, EmailSenderException {

        this.authService.resetPasswordStep1(resetPasswordDto);

        return ResponseSimpleDto.builder()
                .message("Email sent.")
                .details("Check the in mail box.")
                .build();

    }

    // Reset Password Step 2
    @PutMapping(Endpoints.AUTH_UPDATE_PASS)
    public ResponseSimpleDto resetPassword(@RequestBody @Valid ResetPasswordStep2Dto resetPasswordDto) throws ResetCodeExpiredException, ResetCodeNotFoundException, ResetCodeNotMatchingException, EmailNotFoundException {

        this.authService.resetPasswordStep2(resetPasswordDto);

        return ResponseSimpleDto.builder()
                .message("Password changed.")
                .details("Try logging with the new credentials.")
                .build();
    }

}
