package com.tplate.layers.a.rest.dtos.user;

import lombok.Data;

/**
 * Output DTO. Must not have javax validation.
 */

@Data
public class CredentialsResponseDto {

    private String username;

}
