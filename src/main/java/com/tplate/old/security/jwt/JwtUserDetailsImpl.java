package com.tplate.old.security.jwt;

import com.tplate.layers.c.persistence.models.Permission;
import com.tplate.layers.c.persistence.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Builder
@Setter
public class JwtUserDetailsImpl implements UserDetails {

    private User user;

    public JwtUserDetailsImpl(User user) {
        this.user = user;

        // Bug Lazy initialization into filter authorization class
        this.user.getRole().setPermissions(
                new ArrayList<>(user.getRole().getPermissions())
        );
        this.user.setUsername(user.getUsername());
        this.user.setPassword(user.getPassword());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getPermissions();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
