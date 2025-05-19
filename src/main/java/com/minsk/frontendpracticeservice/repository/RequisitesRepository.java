package com.minsk.frontendpracticeservice.repository;

import com.minsk.frontendpracticeservice.domain.entity.Requisites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequisitesRepository extends JpaRepository<Requisites, UUID> {

    boolean existsByAccountNumber(String accountNumber);

}
