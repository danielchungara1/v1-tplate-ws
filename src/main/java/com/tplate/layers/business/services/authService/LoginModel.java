package com.tplate.layers.business.services.authService;

import com.tplate.layers.persistence.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {

    private String token;
    private User user;

}
