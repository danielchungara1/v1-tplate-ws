package com.tplate.layers.business.validators;

import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.persistence.repositories.RoleRepository;
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
class RoleValidatorTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleValidator roleValidator;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guaranteeExistById_withoutExistingId() {
        final Long NOT_EXIST = 1L;

        Mockito.when(roleRepository.existsById(NOT_EXIST))
                .thenReturn(false);

        assertThatThrownBy(() -> this.roleValidator.guaranteeExistById(NOT_EXIST))
                .isInstanceOf(RoleNotExistException.class);
    }
}