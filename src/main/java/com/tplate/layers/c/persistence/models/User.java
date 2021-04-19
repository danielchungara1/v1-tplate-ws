package com.tplate.layers.c.persistence.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contact_id")
    @Valid
    private Contact contact;

    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credentials_id")
    private Credentials credentials;

    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "password_recovery_id")
    private PasswordRecovery passwordRecovery;

    @Transient
    private String token;

}
