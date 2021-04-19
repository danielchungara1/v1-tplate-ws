package com.tplate.layers.c.persistence.repositories;

import com.tplate.layers.c.persistence.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByEmail(String email);

    Optional<Contact> getContactByEmail(String email);

}
