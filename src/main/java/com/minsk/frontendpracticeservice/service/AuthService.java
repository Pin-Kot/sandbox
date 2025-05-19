package com.minsk.frontendpracticeservice.service;

import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.request.AuthRequest;
import com.minsk.frontendpracticeservice.domain.response.JwtResponse;
import com.minsk.frontendpracticeservice.exception.auth.AuthException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtService jwtService;

    private final Map<String, String> refreshStorage = new ConcurrentHashMap<>();

    public JwtResponse authAndGetToken(AuthRequest authRequest) {
        final User user = userService.findByLogin(authRequest.login());

        if (!user.getPassword().equals(authRequest.password())) {
            throw new AuthException("Некорректный пароль");
        }

        final String accessToken = jwtService.generateAccessToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        refreshStorage.put(user.getLogin(), refreshToken);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findByLogin(login);
                final String accessToken = jwtService.generateAccessToken(user);
                return new JwtResponse(accessToken, jwtService.generateRefreshToken(user));
            }
        }
        return new JwtResponse(null, null);
    }

}
