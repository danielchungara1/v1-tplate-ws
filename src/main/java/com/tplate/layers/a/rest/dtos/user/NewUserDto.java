package com.tplate.layers.a.rest.dtos.user;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class NewUserDto {

    @NotNull (message = "credentials is required")
    @Valid
    private CredentialsDto credentials;

    @NotNull (message = "contact is required")
    @Valid
    private ContactDto contact;

    @NotNull(message = "roleId is required")
    private Long roleId;

}
