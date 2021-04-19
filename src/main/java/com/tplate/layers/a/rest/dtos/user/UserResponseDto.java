package com.tplate.layers.a.rest.dtos.user;

import lombok.Data;

/**
 * Output DTO. Must not have javax validation.
 */

@Data
public class UserResponseDto {
    private Long id;
    private ContactDto contact;
    private CredentialsResponseDto credentials;
}
