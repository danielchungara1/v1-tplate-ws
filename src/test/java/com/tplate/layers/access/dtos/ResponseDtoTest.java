package com.tplate.layers.access.dtos;

import com.tplate.layers.access.dtos.user.UserResponseDto;
import com.tplate.layers.persistence.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ResponseDtoTest {

    private User userModel;

    @BeforeEach
    public void beforeEach() {

        this.userModel = User.builder()
                .id(1L)
                .name("Admin")
                .email("danielchungara1@gmail.com")
                .username("administrator")
                .password("123456")
                .phone("1144367326")
                .role(null)
                .token(null)
                .passwordRecovery(null)
                .build();
    }

    @Test
    void buildResponseDto_withDataEqualsUserResponseDto() {

        ResponseDto responseDto = ResponseDto.builder()
                .data(userModel, UserResponseDto.class)
                .build();

        UserResponseDto userResponseDto = (UserResponseDto) responseDto.getData();

        assertThat(userResponseDto.getId()).isEqualTo(userModel.getId());
        assertThat(userResponseDto.getName()).isEqualTo(userModel.getName());
        assertThat(userResponseDto.getEmail()).isEqualTo(userModel.getEmail());
        assertThat(userResponseDto.getUsername()).isEqualTo(userModel.getUsername());
        assertThat(userResponseDto.getPhone()).isEqualTo(userModel.getPhone());

    }

}