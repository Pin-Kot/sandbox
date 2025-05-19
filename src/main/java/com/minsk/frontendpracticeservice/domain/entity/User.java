package com.minsk.frontendpracticeservice.domain.entity;

import com.minsk.frontendpracticeservice.security.Role;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Column(name = "snils", nullable = false)
    private String snils;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "requisites_id", referencedColumnName = "id", nullable = false)
    private Requisites requisites;

}
