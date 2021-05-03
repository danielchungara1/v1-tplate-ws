package com.tplate.layers.access.shared;

import com.tplate.ContainersTests;
import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.business.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.*;

class ControllerExceptionHandlerTest extends ContainersTests {

    @Autowired
    ControllerExceptionHandler controllerExceptionHandler;

    @Test
    void badRequestExceptionHandler_expectResponseSimpleDto() {

        BusinessException exception = new BusinessException(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        ResponseSimpleDto response = controllerExceptionHandler.badRequestExceptionHandler(exception);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotBlank();
        assertThat(response.getDetails()).isNotBlank();
    }


    @Test
    void notFoundExceptionHandler_expectResponseSimpleDto() {
        NoHandlerFoundException exception = new NoHandlerFoundException(UUID.randomUUID().toString(), UUID.randomUUID().toString(), new HttpHeaders());
        WebRequest webRequest = Mockito.mock(WebRequest.class);

        ResponseSimpleDto response = controllerExceptionHandler.notFoundExceptionHandler(exception, webRequest);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotBlank();
        assertThat(response.getDetails()).isNotBlank();
    }

    @Test
    void forbiddenExceptionHandler_expectResponseSimpleDto() {
        AccessDeniedException exception = new AccessDeniedException(UUID.randomUUID().toString());

        ResponseSimpleDto response = controllerExceptionHandler.forbiddenExceptionHandler(exception);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotBlank();
        assertThat(response.getDetails()).isNotBlank();
    }

    @Test
    void allExceptionHandler_expectResponseSimpleDto() {

        Exception exception = new Exception(UUID.randomUUID().toString());

        ResponseSimpleDto response = controllerExceptionHandler.allExceptionHandler(exception);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotBlank();
        assertThat(response.getDetails()).isNotBlank();

    }
}