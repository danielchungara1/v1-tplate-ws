package com.tplate.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Autowired
    ObjectMapper objectMapper;

    private static final String CONTENT_TYPE_JSON = "application/json";


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        }
        catch (ExpiredJwtException e){
            log.error("JWT exception. {}", e.getClass().getCanonicalName());

            response.setContentType(CONTENT_TYPE_JSON);
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            ResponseSimpleDto responseSimpleDto = ResponseSimpleDto.builder()
                    .message("Token expired.")
                    .details("Try logging again.")
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(responseSimpleDto));

        }
        catch (UsernameNotFoundException e){
            log.error("JWT exception. {}", e.getClass().getCanonicalName());

            response.setContentType(CONTENT_TYPE_JSON);
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            ResponseSimpleDto responseSimpleDto = ResponseSimpleDto.builder()
                    .message("Username not exist.")
                    .details("Try logging again.")
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(responseSimpleDto));

        }
        catch (UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException e) {

            log.error("JWT exception. {}", e.getClass().getCanonicalName());

            response.setContentType(CONTENT_TYPE_JSON);
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            ResponseSimpleDto responseSimpleDto = ResponseSimpleDto.builder()
                    .message("Invalid token.")
                    .details(e.getMessage())
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(responseSimpleDto));

        }
        catch (Exception e){

            e.printStackTrace();

            log.error("Unexpected exception. {}", e.getClass().getCanonicalName());

            response.setContentType("application/json");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            ResponseSimpleDto responseSimpleDto = ResponseSimpleDto.builder()
                    .message(e.getMessage())
                    .details(e.getClass().getCanonicalName())
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(responseSimpleDto));

        }
    }
}