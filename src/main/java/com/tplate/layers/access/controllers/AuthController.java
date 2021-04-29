package com.tplate.layers.access.controllers;

import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.LoginResponseDto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep1Dto;
import com.tplate.layers.access.dtos.auth.ResetPasswordStep2Dto;
import com.tplate.layers.business.exceptions.EmailNotFoundException;
import com.tplate.layers.business.exceptions.ResetCodeExpiredException;
import com.tplate.layers.business.exceptions.ResetCodeNotFoundException;
import com.tplate.layers.business.exceptions.ResetCodeNotMatchingException;
import com.tplate.layers.business.services.AuthService;
import com.tplate.layers.access.shared.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseDto login(@RequestBody LoginDto loginDto) throws AuthenticationException {
        return ResponseDto.builder()
                .message("User logged.")
                .details("User logged successfully.")
                .data(this.authService.login(loginDto), LoginResponseDto.class)
                .build();
    }

    // Reset Password Step 1
    @PostMapping("/password/reset-code")
    public ResponseEntity resetPassword(@RequestBody(required=true) @Valid ResetPasswordStep1Dto resetPasswordDto) throws EmailNotFoundException {
        return this.authService.resetPasswordStep1(resetPasswordDto);
    }

    // Reset Password Step 2
    @PutMapping("/password")
    public ResponseEntity resetPassword(@RequestBody(required=true) @Valid ResetPasswordStep2Dto resetPasswordDto) throws ResetCodeExpiredException, ResetCodeNotFoundException, ResetCodeNotMatchingException, EmailNotFoundException {
        return this.authService.resetPasswordStep2(resetPasswordDto);
    }

}
