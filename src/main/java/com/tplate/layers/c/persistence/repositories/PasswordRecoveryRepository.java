package com.tplate.layers.c.persistence.repositories;

import com.tplate.layers.c.persistence.models.PasswordRecovery;
import com.tplate.layers.c.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {
}
