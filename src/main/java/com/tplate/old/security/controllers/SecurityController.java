package com.tplate.old.security.controllers;

import com.tplate.old.security.dtos.ResetPasswordStep1Dto;
import com.tplate.old.security.dtos.ResetPasswordStep2Dto;
import com.tplate.old.security.services.SecurityService;
import com.tplate.old.security.dtos.LoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "auth-controller")
public class SecurityController {

    @Autowired
    SecurityService securityService;

    // Login
    @PostMapping("/token")
    public ResponseEntity login(@RequestBody LoginDto loginDto)  {
        return this.securityService.loguear(loginDto);
    }

//    // Reset Password Step 1
//    @PostMapping("/password/reset_code")
//    public ResponseEntity resetPassword(@RequestBody(required=true) ResetPasswordStep1Dto resetPasswordDto){
//        return this.securityService.resetPasswordStep1(resetPasswordDto);
//    }
//
//    // Reset Password Step 2
//    @PutMapping("/password")
//    public ResponseEntity resetPassword(@RequestBody(required=true) ResetPasswordStep2Dto resetPasswordDto){
//        return this.securityService.resetPasswordStep2(resetPasswordDto);
//    }

}
