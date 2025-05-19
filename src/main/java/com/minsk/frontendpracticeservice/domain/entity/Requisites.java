package com.minsk.frontendpracticeservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requisites")
public class Requisites {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "bic", nullable = false)
    private String bic;

    @Column(name = "correspondent_account", nullable = false)
    private String correspondentAccount;

    @Column(name = "inn", nullable = false)
    private String inn;

    @Column(name = "kpp", nullable = false)
    private String kpp;

    @Column(name = "kbk", nullable = false)
    private String kbk;

}
