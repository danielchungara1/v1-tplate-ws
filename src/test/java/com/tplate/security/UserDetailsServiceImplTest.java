package com.tplate.security;

import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.*;

class UserDetailsServiceImplTest extends BasePostgreContainerTests {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;

    static final String USERNAME_NOT_FOUND = UUID.randomUUID().toString();
    static final Long USER_EXISTING = 1L;

    @Test
    @Transactional
    void loadUserByUsername_withExistingUsername() {
        User userExisting = userRepository.getOneById(USER_EXISTING);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userExisting.getUsername());

        assertThat( userDetails ).isNotNull();
        assertThat( userDetails.getUsername() ).isEqualTo(userExisting.getUsername());
        assertThat( userDetails.getPassword() ).isEqualTo(userExisting.getPassword());
        assertThat( userDetails.getAuthorities() ).isNotNull();
        assertThat( userDetails.getAuthorities().size() ).isEqualTo(userExisting.getRole().getPermissions().size());

        assertThat( userDetails.isAccountNonExpired() ).isTrue();
        assertThat( userDetails.isAccountNonLocked() ).isTrue();
        assertThat( userDetails.isEnabled() ).isTrue();
        assertThat( userDetails.isCredentialsNonExpired() ).isTrue();

    }

    @Test
    void loadUserByUsername_withNonExistingUsername() {
        assertThatThrownBy(
                () -> this.userDetailsService.loadUserByUsername(USERNAME_NOT_FOUND)
        ).isInstanceOf(UsernameNotFoundException.class);
    }

}