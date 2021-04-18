package com.tplate.layers.b.business.builders;

import com.tplate.layers.a.rest.dtos.user.NewUserDto;
import com.tplate.layers.c.persistence.repositories.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
@Disabled
class UserBuilderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserBuilder userBuilder;

    @Autowired
    RoleRepository roleRepository;

    private NewUserDto userDto;

    @BeforeAll
    public static void beforeAll(){}

    @AfterAll
    public static void afterAll(){}

    @BeforeEach
    public void beforeEach(){
//        this.userDto = NewUserDto.builder()
//                .email("danielchungara1@gmail.com")
//                .lastname("Chungara")
//                .name("Daniel")
//                .username("administrador")
//                .phone("11-44367326")
//                .password("123")
//                .roleId(1L)
//                .build();
    }

    @AfterEach
    public void afterEach(){}

    @Test
    void buildFromDtoTest() {

//        User newUser = this.userBuilder.buildModel(this.userDto);
//
//        Assertions.assertEquals(newUser.getEmail(), this.userDto.getEmail());
//        Assertions.assertEquals(newUser.getLastname(), this.userDto.getLastname());
//        Assertions.assertEquals(newUser.getName(), this.userDto.getName());
//        Assertions.assertEquals(newUser.getUsername(), this.userDto.getUsername());
//        Assertions.assertEquals(newUser.getPhone(), this.userDto.getPhone());
//        Assertions.assertTrue(passwordEncoder.matches("123", newUser.getPassword()));
//        Assertions.assertEquals(newUser.getRole(), this.roleRepository.findById(1L));
//
//        log.info("Raw password {}, encoded {}", this.userDto.getPassword(), this.passwordEncoder.encode(userDto.getPassword()));

    }
}