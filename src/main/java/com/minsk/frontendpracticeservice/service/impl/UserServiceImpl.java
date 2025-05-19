package com.minsk.frontendpracticeservice.service.impl;

import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.response.UserRequisitesProjection;
import com.minsk.frontendpracticeservice.exception.business.UserNotFoundException;
import com.minsk.frontendpracticeservice.repository.UserRepository;
import com.minsk.frontendpracticeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_WITH_LOGIN_NOT_FOUND_MESSAGE = "Пользователь с таким логином %s не найден";
    private static final String USER_WITH_ID_NOT_FOUND_MESSAGE = "Пользователь с таким id %s не найден";

    private final UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(USER_WITH_LOGIN_NOT_FOUND_MESSAGE.formatted(login)));
    }

    @Override
    public UserRequisitesProjection findUserRequisitesProjectionById(String userId) {
        UUID id = UUID.fromString(userId);
        return userRepository.findUserRequisitesProjectionById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_WITH_ID_NOT_FOUND_MESSAGE.formatted(id)));
    }

}
