package com.tplate.security.jwt;

import com.tplate.BasePostgreContainerTests;
import com.tplate.layers.access.shared.HttpConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import java.io.IOException;

@Slf4j
class JwtExceptionHandlerFilterTest extends BasePostgreContainerTests {

    @Autowired
    JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    @Mock
    FilterChain filterChain;

    MockHttpServletRequest req;
    MockHttpServletResponse res;

    @BeforeEach
    public void beforeEach() {
        this.req = new MockHttpServletRequest();
        this.res = new MockHttpServletResponse();
    }

    @AfterEach
    public void afterEach() {
        this.req = null;
        this.res = null;
    }

    @Test
    void doFilterInternal_throwsExpiredJwtException() throws IOException, ServletException {

        Mockito.doThrow(ExpiredJwtException.class).when(filterChain).doFilter(req,res);

        jwtExceptionHandlerFilter.doFilterInternal(req, res, filterChain);

            assertThat(res.getContentAsString()).isNotNull();
        assertThat(res.getContentType()).isEqualTo(HttpConstants.CONTENT_TYPE_JSON);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        log.info(">>> Content written." + res.getContentAsString());
    }

    @Test
    void doFilterInternal_throwsUsernameNotFoundException() throws IOException, ServletException {

        Mockito.doThrow(UsernameNotFoundException.class).when(filterChain).doFilter(req,res);

        jwtExceptionHandlerFilter.doFilterInternal(req, res, filterChain);

        assertThat(res.getContentAsString()).isNotNull();
        assertThat(res.getContentType()).isEqualTo(HttpConstants.CONTENT_TYPE_JSON);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        log.info(">>> Content written." + res.getContentAsString());
    }

    @Test
    void doFilterInternal_throwsUnsupportedJwtException() throws IOException, ServletException {

        Mockito.doThrow(UnsupportedJwtException.class).when(filterChain).doFilter(req,res);

        jwtExceptionHandlerFilter.doFilterInternal(req, res, filterChain);

        assertThat(res.getContentAsString()).isNotNull();
        assertThat(res.getContentType()).isEqualTo(HttpConstants.CONTENT_TYPE_JSON);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        log.info(">>> Content written." + res.getContentAsString());
    }


    @Test
    void doFilterInternal_throwsException() throws IOException, ServletException {

        Mockito.doThrow(RuntimeException.class).when(filterChain).doFilter(req,res);

        jwtExceptionHandlerFilter.doFilterInternal(req, res, filterChain);

        assertThat(res.getContentAsString()).isNotNull();
        assertThat(res.getContentType()).isEqualTo(HttpConstants.CONTENT_TYPE_JSON);
        assertThat(res.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

        log.info(">>> Content written." + res.getContentAsString());
    }
}