package com.tplate.layers.business.services;

import com.tplate.layers.business.exceptions.EmailExistException;
import com.tplate.layers.business.exceptions.RoleNotExistException;
import com.tplate.layers.business.exceptions.UserNotExistException;
import com.tplate.layers.business.exceptions.UsernameExistException;
import com.tplate.layers.access.dtos.user.UserBaseDto;
import com.tplate.layers.access.dtos.user.UserNewDto;
import com.tplate.layers.business.validators.UserValidator;
import com.tplate.layers.persistence.models.User;
import com.tplate.layers.access.dtos.user.UserUpdateDto;
import com.tplate.layers.persistence.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional(rollbackFor = Exception.class)
    public User saveModel(UserNewDto dto) throws RoleNotExistException, UsernameExistException, EmailExistException {

        User user = User.builder().build();

        // Password
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        // Email
        this.userValidator.guaranteeNotExistEmail(dto.getEmail());
        user.setEmail(dto.getEmail());

        // Username
        this.userValidator.guaranteeNotExistUsername(dto.getUsername());
        user.setUsername(dto.getUsername());

        return this.saveOrUpdateModel(user, dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateModel(UserUpdateDto dto, Long id) throws RoleNotExistException, UsernameExistException, EmailExistException, UserNotExistException {

        User user = this.getModelById(id);

        // Password Optional
        if (dto.getPassword() != null) {
            user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }

        // Email False Positive.
        if ( !this.userRepository.existsByEmail(dto.getEmail()) ) {
            user.setEmail(dto.getEmail());
        } else {
            if (user.getEmail().equals(dto.getEmail())) {
                // False Positive
            } else {
                this.userValidator.throwsEmailExistException();
            }
        }

        // Username False Positive.
        if ( !this.userRepository.existsByUsername(dto.getUsername()) ) {
            user.setUsername(dto.getUsername());
        } else {
            if (user.getUsername().equals(dto.getUsername())) {
                // False Positive
            } else {
                this.userValidator.throwsUsernameExistException();
            }
        }

        return this.saveOrUpdateModel( user, dto );
    }

    @Transactional
    public User saveOrUpdateModel(User user, UserBaseDto dto) throws RoleNotExistException {

        user.setRole(this.roleService.getModelById(dto.getRoleId()));
        user.setLastname(dto.getLastname());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());

        return this.userRepository.save(user);

    }

    @Transactional
    public void deleteModelById(Long id) throws UserNotExistException {
        this.userValidator.guaranteeExistById(id);
        this.userRepository.deleteById(id);
    }

    @Transactional
    public User getModelById(Long id) throws UserNotExistException {

        this.userValidator.guaranteeExistById(id);

        return this.userRepository.getOne(id);
    }

    @Transactional
    public Page findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }
}
