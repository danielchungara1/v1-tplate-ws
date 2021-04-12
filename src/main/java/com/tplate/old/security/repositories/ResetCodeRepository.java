package com.tplate.old.security.repositories;

import com.tplate.old.security.models.ResetPassword;
import com.tplate.layers.b.business.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetCodeRepository extends JpaRepository<ResetPassword, Long> {
    Optional<ResetPassword> findByUser(User user);
}
