package com.tplate.layers.persistence.repositories;

import com.tplate.layers.persistence.models.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {
}
