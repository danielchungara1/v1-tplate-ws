package com.tplate.layers.business.services;

import com.tplate.layers.access.dtos.user.UserBaseDto;
import com.tplate.layers.access.dtos.user.UserNewDto;
import com.tplate.layers.access.specifications.UserSpecification;
import com.tplate.layers.business.exceptions.BusinessException;
import com.tplate.layers.business.exceptions.auth.EmailExistException;
import com.tplate.layers.business.exceptions.auth.EmailNotFoundException;
import com.tplate.layers.business.exceptions.role.RoleNotExistException;
import com.tplate.layers.business.exceptions.user.UserCannotBeDeleteException;
import com.tplate.layers.business.exceptions.user.UserNotExistException;
import com.tplate.layers.business.exceptions.user.UsernameExistException;
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

import java.util.List;

@Service
@Log4j2
public class UserService {

    @Autowired
    UserRepository userRepository;

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
        if (this.userRepository.existsByEmail(dto.getEmail())) {
            EmailExistException.throwsException(dto.getEmail());
        }
        user.setEmail(dto.getEmail());

        // Username
        if (this.userRepository.existsByUsername(dto.getUsername())) {
            UsernameExistException.throwsException(dto.getUsername());
        }
        user.setUsername(dto.getUsername());

        return this.saveOrUpdateModel(user, dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateModel(UserUpdateDto dto, Long id) throws UserNotExistException, EmailExistException, UsernameExistException, RoleNotExistException, BusinessException {

        User user = this.getModelById(id);

        // If user is the admin the operation is not permitted.
        this.adminCannotBeAltered(id, "It is not permitted update the admin profile.");

        // Password Optional
        if (dto.getPassword() != null && !dto.getPassword().equals("")) {
            user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }

        // Email False Positive.
        if ( !this.userRepository.existsByEmail(dto.getEmail()) ) {
            user.setEmail(dto.getEmail());
        } else {
            if (user.getEmail().equals(dto.getEmail())) {
                // False Positive
            } else {
                EmailExistException.throwsException(dto.getEmail());
            }
        }

        // Username False Positive.
        if ( !this.userRepository.existsByUsername(dto.getUsername()) ) {
            user.setUsername(dto.getUsername());
        } else {
            if (user.getUsername().equals(dto.getUsername())) {
                // False Positive
            } else {
                UsernameExistException.throwsException(dto.getUsername());
            }
        }

        return this.saveOrUpdateModel( user, dto );
    }

    private User saveOrUpdateModel(User user, UserBaseDto dto) throws RoleNotExistException {

        user.setRole(this.roleService.getModelById(dto.getRoleId()));
        user.setLastname(dto.getLastname());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());

        return this.userRepository.save(user);

    }

    @Transactional
    public void deleteModelById(Long id) throws UserNotExistException, UserCannotBeDeleteException, BusinessException {

        if (!this.userRepository.existsById(id)) {
            UserNotExistException.throwsException(id);
        }

        if (!this.userRepository.getOneById(id).getDeletable()) {
            UserCannotBeDeleteException.throwsException(id);
        }

        this.userRepository.deleteById(id);
    }

    @Transactional
    public User getModelById(Long id) throws UserNotExistException {

        if (!this.userRepository.existsById(id)) {
            UserNotExistException.throwsException(id);
        }

        return this.userRepository.getOneById(id);

    }

    @Transactional
    public Page find(Pageable pageable, UserSpecification specification) {
        return this.userRepository.findAll(specification, pageable);
    }

    @Transactional
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public User getModelByEmail(String email) throws EmailNotFoundException {
        if (!this.userRepository.existsByEmail(email)) {
            EmailNotFoundException.throwsException(email);
        }

        return this.userRepository.getByEmail(email);
    }

    private void adminCannotBeAltered(Long id, String msg) throws BusinessException {
        if (id.equals(1L)) {
            throw new BusinessException(msg, msg);
        }
    }
}
