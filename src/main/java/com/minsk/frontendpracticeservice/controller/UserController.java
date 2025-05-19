package com.minsk.frontendpracticeservice.controller;

import com.minsk.frontendpracticeservice.domain.response.UserRequisitesProjection;
import com.minsk.frontendpracticeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/requisites/{userId}")
    public ResponseEntity<UserRequisitesProjection> getUserRequisites(@PathVariable @UUID String userId) {
        return ResponseEntity
                .ok(userService.findUserRequisitesProjectionById(userId));
    }

}
