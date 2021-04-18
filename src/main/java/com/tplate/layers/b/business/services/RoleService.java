package com.tplate.layers.b.business.services;

import com.tplate.layers.b.business.exceptions.RoleNotFoundException;
import com.tplate.layers.b.business.validators.RoleValidator;
import com.tplate.layers.c.persistence.models.Role;
import com.tplate.layers.c.persistence.repositories.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleValidator roleValidator;

//    @Transactional
//    public ResponseEntity editProfile(UserProfileDto userProfileDto, Long idUser) throws UserExistException {
//
//        // Validator responsibilities
//        userValidator.validateMustNotExist(idUser);
//
//        // User Builder responsibilities
//        User newUser = userBuilder.buildFrom(newUserDto);
//
//        // Persistence layer responsibilities
//        User userCreated = this.userRepository.save(newUser);
//
//        // Response Builder responsibilities
//        return ResponseBuilder.builder()
//                .ok()
//                .message("User registered.")
//                .dto(userCreated, UserDto.class)
//                .build();
//
//
//
//        try {
//
//            // Validate existence
//            this.userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
//
//            // Save profile
//            User user = this.userRepository.getOne(idUser);
//            user.setName(userProfileDto.getName());
//            user.setLastname(userProfileDto.getLastname());
//            user.setPhone(userProfileDto.getPhone());
//            user.setEmail(userProfileDto.getEmail());
//            User user_ = this.userRepository.save(user);
//
//            // Response
//            return ResponseBuilder.builder()
//                    .ok()
//                    .dto(user_, UserProfileDto.class)
//                    .message("Profile edited.")
//                    .build();
//        } catch (UserNotFoundException e) {
//            log.error("User not found. {}", e.getMessage());
//            return ResponseBuilder.buildBadRequest(e.getMessage());
//
//        } catch (Exception e) {
//            log.error("Something went wrong. {}, {}", e.getMessage(), e.getClass().getCanonicalName());
//            return ResponseBuilder.buildSomethingWrong(e.getMessage());
//        }
//    }

//    @Transactional
//    public ResponseEntity getProfile(Long idUser) throws UserNotFoundException {
//
//        // Validate existence
//        this.userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
//
//        // Get profile
//        User user = this.userRepository.getOne(idUser);
//
//        // Response
//        return ResponseBuilder.builder()
//                .ok()
//                .dto(user, UserProfileDto.class)
//                .message("Profile.")
//                .build();
//    }

    @Transactional
    public Role getModelBy(Long id)  throws RoleNotFoundException{

        this.roleValidator.guaranteeExistById(id);

        return this.roleRepository.getOne(id);
    }

}
