package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.LoginResponseDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.layers.business.exceptions.*;
import com.tplate.layers.business.services.AuthService;
import com.tplate.layers.access.shared.Paths;
import com.tplate.security.jwt.JwtCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(Paths.AUTH)
public class AuthController {

    @Autowired
    AuthService authService;

    // Login
    @PostMapping("/login")
    public ResponseDto login(@RequestBody @Valid LoginDto loginDto) throws AuthenticationException, JwtCustomException {
        return ResponseDto.builder()
                .message("User logged.")
                .details("User logged successfully.")
                .data(this.authService.login(loginDto), LoginResponseDto.class)
                .build();
    }

    // Reset Password Step 1
    @PostMapping("/password/reset-code")
    public ResponseSimpleDto resetPassword(@RequestBody @Valid ResetPasswordStep1Dto resetPasswordDto)
            throws EmailNotFoundException, EmailSenderException {

        this.authService.resetPasswordStep1(resetPasswordDto);

        return ResponseSimpleDto.builder()
                .message("Email was sent successfully.")
                .details("Check the in mail box.")
                .build();

    }

    // Reset Password Step 2
    @PutMapping("/password")
    public ResponseSimpleDto resetPassword(@RequestBody @Valid ResetPasswordStep2Dto resetPasswordDto) throws ResetCodeExpiredException, ResetCodeNotFoundException, ResetCodeNotMatchingException, EmailNotFoundException {

        this.authService.resetPasswordStep2(resetPasswordDto);

        return ResponseSimpleDto.builder()
                .message("The password has been changed.")
                .details("Try logging with the new credentials.")
                .build();
    }

}
