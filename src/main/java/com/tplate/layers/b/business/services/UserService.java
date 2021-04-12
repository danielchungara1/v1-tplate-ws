package com.tplate.layers.b.business.services;

import com.tplate.layers.a.rest.dtos.user.UserDto;
import com.tplate.layers.b.business.builders.ResponseBuilder;
import com.tplate.layers.b.business.builders.UserBuilder;
import com.tplate.layers.b.business.exceptions.restexceptions.UserDtoException;
import com.tplate.layers.b.business.models.User;
import com.tplate.layers.b.business.validators.UserValidator;
import com.tplate.layers.b.business.exceptions.restexceptions.UserNotFoundException;
import com.tplate.layers.a.rest.dtos.user.NewUserDto;
import com.tplate.layers.b.business.exceptions.restexceptions.UserExistException;
import com.tplate.old.security.rol.RolRepository;
import com.tplate.layers.a.rest.dtos.user.UserProfileDto;
import com.tplate.layers.c.persistence.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UserValidator userValidator;

    @Autowired
    UserBuilder userBuilder;

    @Transactional
    public ResponseEntity editProfile(UserProfileDto userProfileDto, Long idUser) {

        try {

            // Validate existence
            this.userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);

            // Save profile
            User user = this.userRepository.getOne(idUser);
            user.setName(userProfileDto.getName());
            user.setLastname(userProfileDto.getLastname());
            user.setPhone(userProfileDto.getPhone());
            user.setEmail(userProfileDto.getEmail());
            User user_ = this.userRepository.save(user);

            // Response
            return ResponseBuilder.builder()
                    .ok()
                    .dto(user_, UserProfileDto.class)
                    .message("Profile edited.")
                    .build();
        } catch (UserNotFoundException e) {
            log.error("User not found. {}", e.getMessage());
            return ResponseBuilder.buildBadRequest(e.getMessage());

        } catch (Exception e) {
            log.error("Something went wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
            return ResponseBuilder.buildSomethingWrong(e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity getProfile(Long idUser) {

        try {

            // Validate existence
            this.userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);

            // Get profile
            User user = this.userRepository.getOne(idUser);

            // Response
            return ResponseBuilder.builder()
                    .ok()
                    .dto(user, UserProfileDto.class)
                    .message("Profile.")
                    .build();
        } catch (UserNotFoundException e) {
            log.error("User not found. {}", e.getMessage());
            return ResponseBuilder.buildBadRequest(e.getMessage());

        } catch (Exception e) {
            log.error("Something went wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
            return ResponseBuilder.buildSomethingWrong(e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity createUser(NewUserDto newUserDto) throws UserDtoException, UserExistException {

            // Validator responsibilities
            userValidator.validate(newUserDto);
            userValidator.validateIfExist(newUserDto.getUsername());

            // User builder responsibilities
            User newUser = userBuilder.buildFrom(newUserDto);

            // Persistence layer responsibilities
            User userCreated = this.userRepository.save(newUser);

            // Response builder responsibilities
            return ResponseBuilder.builder()
                    .ok()
                    .message("User was registered.  " + newUser.getUsername())
                    .dto(userCreated, UserDto.class)
                    .build();
    }

}
