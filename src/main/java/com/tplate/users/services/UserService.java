package com.tplate.users.services;

import com.tplate.exceptions.FormValidatorException;
import com.tplate.exceptions.UserNotFoundException;
import com.tplate.builders.ResponseEntityBuilder;
import com.tplate.security.dtos.SingUpDto;
import com.tplate.security.exceptions.UsernameExistsException;
import com.tplate.security.rol.RolRepository;
import com.tplate.users.dtos.UserProfileDto;
import com.tplate.users.models.User;
import com.tplate.users.repositories.UserRepository;
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
            return ResponseEntityBuilder.builder()
                    .ok()
                    .dto(user_, UserProfileDto.class)
                    .message("Profile edited.")
                    .build();
        } catch (UserNotFoundException e) {
            log.error("User not found. {}", e.getMessage());
            return ResponseEntityBuilder.buildBadRequest(e.getMessage());

        } catch (Exception e) {
            log.error("Something went wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
            return ResponseEntityBuilder.buildSomethingWrong(e.getMessage());
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
            return ResponseEntityBuilder.builder()
                    .ok()
                    .dto(user, UserProfileDto.class)
                    .message("Profile.")
                    .build();
        } catch (UserNotFoundException e) {
            log.error("User not found. {}", e.getMessage());
            return ResponseEntityBuilder.buildBadRequest(e.getMessage());

        } catch (Exception e) {
            log.error("Something went wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
            return ResponseEntityBuilder.buildSomethingWrong(e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity signUp(SingUpDto singUpDto) {

        try {

            // Validate dto
            singUpDto.validate();

            // Validate username existence
            if (this.userRepository.existsByUsername(singUpDto.getUsername())) {
                log.error("Username exists. {}", singUpDto.getUsername());
                throw new UsernameExistsException();
            }

            // Save new user
            User newUser = User.builder()
                    .username(singUpDto.getUsername())
                    .password(this.passwordEncoder.encode(singUpDto.getPassword()))
                    .rol(this.rolRepository.findByName("ADMINISTRADOR").get())
                    .build();
            this.userRepository.save(newUser);

            // Response
            log.info("User was registered. {}", singUpDto.getUsername());
            return ResponseEntityBuilder.builder()
                    .ok()
                    .message("User was registered.  " + newUser.getUsername())
                    .build();

        } catch (FormValidatorException | UsernameExistsException e) {
            return ResponseEntityBuilder.buildConflict(e.getMessage());
        } catch (Exception e) {
            log.error("Something went wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
            return ResponseEntityBuilder.buildSomethingWrong();
        }
    }

}
