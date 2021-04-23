package com.tplate.layers.business.validators;


import com.tplate.layers.business.exceptions.EmailExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.business.exceptions.UsernameExistException;
import com.tplate.layers.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void guaranteeExistById_withoutExistingId() {
        final Long NOT_EXIST = 1L;

        Mockito.when(userRepository.existsById(NOT_EXIST))
                .thenReturn(false);

        assertThatThrownBy(() -> this.userValidator.guaranteeExistById(NOT_EXIST))
                .isInstanceOf(UserNotExistException.class);
    }

    @Test
    void guaranteeNotExistEmail_withExistingEmail() {
        final String EXIST = "danielchungara1@gmail.com";

        Mockito.when(userRepository.existsByEmail(EXIST))
                .thenReturn(true);

        assertThatThrownBy(() -> this.userValidator.guaranteeNotExistEmail(EXIST))
                .isInstanceOf(EmailExistException.class);
    }

    @Test
    void guaranteeNotExistUsername_withExistingUsername() {
        final String EXIST = "danielchungara1";

        Mockito.when(userRepository.existsByUsername(EXIST))
                .thenReturn(true);

        assertThatThrownBy(() -> this.userValidator.guaranteeNotExistUsername(EXIST))
                .isInstanceOf(UsernameExistException.class);
    }
}