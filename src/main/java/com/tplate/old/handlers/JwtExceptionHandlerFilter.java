package com.tplate.old.handlers;

import com.tplate.layers.admission.dtos.SimpleResponseDto;
import com.tplate.old.util.JsonUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e){
            log.error("JWT exception. {}", e.getClass().getCanonicalName());

            response.setContentType("application/json");
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            SimpleResponseDto simpleResponseDto = SimpleResponseDto.builder()
                    .message("Session finished.")
                    .details(e.getMessage())
                    .build();

            response.getWriter().write(JsonUtil.convertObjectToJson(simpleResponseDto));

        } catch (Exception e){

            e.printStackTrace();

            log.error("JWT exception. {}", e.getClass().getCanonicalName());

            response.setContentType("application/json");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            SimpleResponseDto simpleResponseDto = SimpleResponseDto.builder()
                    .message(e.getMessage())
                    .details(e.getClass().getCanonicalName())
                    .build();

            response.getWriter().write(JsonUtil.convertObjectToJson(simpleResponseDto));

        }
    }
}