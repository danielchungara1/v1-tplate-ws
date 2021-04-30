package com.tplate.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import com.tplate.security.SecurityConstants;
import com.tplate.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@Slf4j
class JwtTokenUtilTest extends BasePostgreContainerTests {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    @Mock
    HttpServletRequest httpServletRequest;

    static final Long USER_EXISTING = 1L;

    static final Long USER_NOT_FOUND = -1L;

    @Test
    @Transactional
    void generateToken_withUserExisting() throws JwtCustomException {

        User user = userRepository.getOneById(USER_EXISTING);

        DecodedJWT token = JWT.decode(this.jwtTokenUtil.generateToken(user));
        assertThat(token).isNotNull();
        assertThat(token.getSubject()).isEqualTo(user.getUsername());
        assertThat(token.getIssuedAt()).isNotNull();
        assertThat(token.getExpiresAt()).isNotNull();
        assertThat(token.getSignature()).isNotNull();
        assertThat(token.getClaims()).isNotNull();
        assertThat(token.getClaims().get(SecurityConstants.JWT_USER_ID).asLong()).isEqualTo(user.getId());
        assertThat(token.getClaims().get(SecurityConstants.JWT_AUTHORITIES_KEY).asList(Object.class).size()).isEqualTo(user.getRole().getPermissions().size());

        log.info(">>> Token {}", token.getToken());

    }

    @Test
    @Transactional
    void generateToken_withNonUserExisting() {

        User user = userRepository.getOneById(USER_NOT_FOUND);

        assertThatThrownBy(() -> this.jwtTokenUtil.generateToken(user))
                .isInstanceOf(JwtCustomException.class);

    }

    @Test
    @Transactional
    void validateToken_withUsernameExistingAndTokenNonExpired() throws JwtCustomException {
        User user = userRepository.getOneById(USER_EXISTING);

        String token = this.jwtTokenUtil.generateToken(user);

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .permissions(user.getRole().getPermissions())
                .build();

        assertThat(this.jwtTokenUtil.validateToken(token, userDetails)).isTrue();

    }

    @Test
    @Transactional
    void validateToken_withNonExistingUsername() throws JwtCustomException {
        User user = userRepository.getOneById(USER_EXISTING);

        String token = this.jwtTokenUtil.generateToken(user);

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .username(UUID.randomUUID().toString())
                .password(user.getPassword())
                .permissions(user.getRole().getPermissions())
                .build();

        assertThat(this.jwtTokenUtil.validateToken(token, userDetails)).isFalse();

    }

    @Test
    @Transactional
    void resolveToken_withValidToken() throws JwtCustomException {

        final String EXPECTED_TOKEN = this.jwtTokenUtil.generateToken(this.userRepository.getOneById(USER_EXISTING));

        Mockito
                .when(this.httpServletRequest.getHeader(SecurityConstants.JWT_HEADER_AUTHORIZATION_KEY))
                .thenReturn(SecurityConstants.JWT_TOKEN_BEAR_PREFIX + EXPECTED_TOKEN);

        assertThat(this.jwtTokenUtil.resolveToken(this.httpServletRequest)).isEqualTo(EXPECTED_TOKEN);

        log.info(">>> Http header authorization. {}", this.httpServletRequest.getHeader(SecurityConstants.JWT_HEADER_AUTHORIZATION_KEY) );
    }

    @Test
    void resolveToken_withNullAuthorizationHeader() throws JwtCustomException {

        final String EXPECTED_RESOLVE = null;

        Mockito
                .when(this.httpServletRequest.getHeader(SecurityConstants.JWT_HEADER_AUTHORIZATION_KEY))
                .thenReturn(null);

        assertThat(this.jwtTokenUtil.resolveToken(this.httpServletRequest)).isEqualTo(EXPECTED_RESOLVE);

    }
}