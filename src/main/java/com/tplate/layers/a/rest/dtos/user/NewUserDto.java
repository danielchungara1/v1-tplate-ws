package com.tplate.layers.a.rest.dtos.user;

import com.tplate.old.exceptions.FormValidatorException;
import com.tplate.layers.b.business.validators.FormValidator;
import lombok.Data;

@Data
public class NewUserDto {
    private String username;
    private String password;

    public void validate() throws FormValidatorException {
        FormValidator.evaluate()
                .isRequired(this.username, "Username")
                .isRequired(this.password, "Password");
    }
}
