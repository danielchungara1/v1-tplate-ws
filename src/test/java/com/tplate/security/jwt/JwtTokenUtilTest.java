package com.tplate.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import com.tplate.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@Slf4j
class JwtTokenUtilTest extends BasePostgreContainerTests {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    static final Long USER_EXISTING = 1L;

    @Test
    @Transactional
    void generateToken() {

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


}