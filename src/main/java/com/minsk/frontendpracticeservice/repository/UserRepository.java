package com.minsk.frontendpracticeservice.repository;

import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.response.UserRequisitesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);

    @Query("""
            SELECT u.firstName AS firstName, r.accountNumber AS accountNumber, r.kbk AS kbk 
            FROM User u 
            JOIN u.requisites r 
            WHERE u.id = :id
            """)
    Optional<UserRequisitesProjection> findUserRequisitesProjectionById(@Param("id") UUID id);
}
