package com.tplate.security.jwt;

import com.tplate.ContainersTests;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.persistence.repositories.UserRepository;
import com.tplate.security.SecurityConstants;
import com.tplate.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import static org.assertj.core.api.AssertionsForClassTypes.*;


@Slf4j
class JwtAuthorizationFilterTest extends ContainersTests {

    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    static final Long USER_EXISTING = 1L;

    @Mock
    HttpServletRequest req;

    MockHttpServletResponse res;
    MockFilterChain filterChain;

    @BeforeEach
    public void beforeEach() {
        this.res = new MockHttpServletResponse();
        this.filterChain = new MockFilterChain();
    }

    @AfterEach
    public void afterEach() {
        this.res = null;
        this.filterChain = null;
    }


    @Test
    @Transactional
    void doFilterInternal_withValidAuthentication() throws ServletException, IOException, JwtCustomException {

        User userExisting = this.userRepository.getOneById(USER_EXISTING);

        Mockito
                .when(req.getHeader(SecurityConstants.JWT_HEADER_AUTHORIZATION_KEY))
                .thenReturn(SecurityConstants.JWT_TOKEN_BEAR_PREFIX + this.jwtTokenUtil.generateToken(userExisting));

        this.jwtAuthorizationFilter.doFilterInternal(req, res, filterChain);

        assertThat(SecurityContextHolder.getContext()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isNotNull();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertThat(userDetails.getUsername()).isEqualTo(userExisting.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(userExisting.getPassword());
        assertThat(userDetails.getAuthorities().size()).isEqualTo(userExisting.getRole().getPermissions().size());

        log.info(">>> Context. {}", SecurityContextHolder.getContext().getAuthentication().toString());

    }
}