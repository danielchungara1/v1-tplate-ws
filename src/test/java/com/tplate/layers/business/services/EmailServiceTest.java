package com.tplate.layers.business.services;

import com.icegreen.greenmail.util.GreenMailUtil;
import com.tplate.ContainersTests;
import com.tplate.layers.business.exceptions.EmailSenderException;
import com.tplate.layers.business.services.authService.EmailResetCode;
import com.tplate.layers.business.services.authService.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.internet.MimeMessage;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.awaitility.Awaitility.await;

@Slf4j
class EmailServiceTest extends ContainersTests {

    @Autowired
    EmailService emailService;

    final String RESET_CODE = "1234";

    final String EMAIL_TO = "danielchungara.test@gmail.com";


    @Test
    void send_withNonResetCode() {
        assertThatThrownBy(() ->
                this.emailService.send(EmailResetCode.builder()
                        .to(EMAIL_TO)
                        .build()
                )
        ).isInstanceOf(EmailSenderException.class);
    }

    @Test
    void send_withNonEmailTo() {
        assertThatThrownBy(() ->
                this.emailService.send(EmailResetCode.builder()
                        .data(RESET_CODE)
                        .build()
                )
        ).isInstanceOf(EmailSenderException.class);
    }

    @Test
    void send_withEmbeddedSMTP() throws EmailSenderException {

        this.emailService.send(
                EmailResetCode.builder()
                        .to(EMAIL_TO)
                        .data(RESET_CODE)
                        .build()
        );

        await().atMost(2, SECONDS).untilAsserted( () -> {

            MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
            assertThat(1).isEqualTo(receivedMessages.length);

            MimeMessage receivedMessage = receivedMessages[0];

            //log.info(">>> Body message. {}", GreenMailUtil.getBody(receivedMessage));
            assertThat(GreenMailUtil.getBody(receivedMessage)).contains(RESET_CODE);

            //log.info(">>> Header message. {}", GreenMailUtil.getHeaders(receivedMessage));
            assertThat(GreenMailUtil.getHeaders(receivedMessage)).contains(EMAIL_TO);
            assertThat(GreenMailUtil.getHeaders(receivedMessage)).contains(EmailResetCode.SUBJECT);

        });
    }

}