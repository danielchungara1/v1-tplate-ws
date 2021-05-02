package com.tplate.layers.business.services;

import com.tplate.PostgreBaseContainerTests;
import com.tplate.layers.business.exceptions.EmailSenderException;
import com.tplate.layers.business.shared.EmailImpl;
import com.tplate.layers.persistence.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@Slf4j
class EmailServiceTest extends PostgreBaseContainerTests {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfig;

    final String RESET_CODE = "1234";

    final String EMAIL_TEST = "danielchungara.test@gmail.com";

    @Test
    void send_withNonResetCode() {
        assertThatThrownBy(() ->
                this.emailService.send(EmailImpl.builder()
                        .to(EMAIL_TEST)
                        .build()
                )
        ).isInstanceOf(EmailSenderException.class);
    }

    @Test
    void send_withNonEmailTo() {
        assertThatThrownBy(() ->
                this.emailService.send(EmailImpl.builder()
                        .data(RESET_CODE)
                        .build()
                )
        ).isInstanceOf(EmailSenderException.class);
    }

}