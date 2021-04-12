package com.tplate.layers.a.rest.dtos.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserProfileDto {

    private String name;
    private String lastname;
    private String email;
    private String phone;

}
