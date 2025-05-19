package com.minsk.frontendpracticeservice.stubs;

import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.security.Role;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class UserStubs {

    public static final UUID USER_ID = UUID.fromString("fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8");
    public static final String USER_FIRST_NAME = "Андрей";
    public static Set<Role> rolesSet = Set.of(Role.USER, Role.ADMIN);

    public static User createValidUserStubs() {
        return User.builder()
                .id(USER_ID)
                .firstName(USER_FIRST_NAME)
                .lastName("Антонов")
                .birthDate(LocalDate.of(2000, 1, 1))
                .inn("468175102208")
                .snils("12345678901")
                .passportNumber("1010111222")
                .login("andrew11")
                .password("andrew11")
                .roles(rolesSet)
                .build();
    }

}
