package com.tplate.layers.access.controllers;

import com.tplate.ContainersTests;
import com.tplate.layers.access.dtos.ResponseDto;
import com.tplate.layers.access.dtos.auth.LoginDto;
import com.tplate.layers.access.dtos.auth.LoginResponseDto;
import com.tplate.layers.business.services.AuthService;
import com.tplate.layers.business.shared.LoginModel;
import com.tplate.security.jwt.JwtCustomException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class AuthControllerTest extends ContainersTests {

    @InjectMocks
    AuthController authController;

    @Mock
    AuthService authService;

    @Test
    void login_expectResponseDtoWithToken() throws JwtCustomException {

        String TOKEN_EXPECTED = UUID.randomUUID().toString();

        Mockito.when(authService.login(Mockito.any())).thenReturn(
                LoginModel.builder()
                        .token(TOKEN_EXPECTED)
                        .build()
        );
        assertThat(this.authController).isNotNull();

        ResponseDto response = this.authController.login(LoginDto.builder().build());
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(((LoginResponseDto)response.getData()).getToken()).isEqualTo(TOKEN_EXPECTED);

    }
}