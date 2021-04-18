package com.tplate.layers.c.persistence.repositories;

import com.tplate.layers.c.persistence.models.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    boolean existsByUsername(String username);

    Optional<Credentials> findByUsername(String username);

}
