package com.tplate.password;

import com.tplate.ContainersTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.*;
@Slf4j
public class PasswordTest extends ContainersTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void encodePasswordTest() {

        final String RAW_PASSWORD = "tplate";
        final String ENCRYPTED_PASSWORD = this.passwordEncoder.encode(RAW_PASSWORD);

        log.info("Password {}, Encrypted {}", RAW_PASSWORD, ENCRYPTED_PASSWORD);

        assertThat(passwordEncoder.matches(RAW_PASSWORD, ENCRYPTED_PASSWORD)).isTrue();


    }
}
