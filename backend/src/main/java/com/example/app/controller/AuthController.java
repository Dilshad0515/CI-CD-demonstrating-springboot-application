package com.example.app.controller;

import com.example.app.model.UserEntity;
import com.example.app.model.Role;
import com.example.app.model.RefreshToken;
import com.example.app.repository.UserRepository;
import com.example.app.security.JwtUtil;
import com.example.app.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

/**
 * Authentication endpoints:
 * - /api/auth/register
 * - /api/auth/login  -> returns access token + refresh token
 * - /api/auth/refresh -> exchange refresh token for new access token
 * - /api/auth/logout  -> revoke refresh tokens for user
 *
 * This is a simple implementation for demos. Harden before production.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequests.RegisterRequest body) {
        if (userRepository.findByUsername(body.username).isPresent()) {
            return "User exists";
        }
        Set<Role> roles = (body.roles == null || body.roles.isEmpty())
                ? Set.of(Role.ROLE_USER)
                : body.roles.stream().map(r -> Role.valueOf(r)).collect(Collectors.toSet());
        UserEntity user = new UserEntity(body.username, passwordEncoder.encode(body.password), roles);
        userRepository.save(user);
        return "OK";
    }

    @PostMapping("/login")
    public AuthRequests.AuthResponse login(@RequestBody AuthRequests.LoginRequest body) {
        var userOpt = userRepository.findByUsername(body.username);
        if (userOpt.isEmpty()) {
            return new AuthRequests.AuthResponse("", "");
        }
        var user = userOpt.get();
        if (!passwordEncoder.matches(body.password, user.getPasswordHash())) {
            return new AuthRequests.AuthResponse("", "");
        }
        var roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        String accessToken = jwtUtil.generateToken(user.getUsername(), roles);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthRequests.AuthResponse(accessToken, refreshToken.getToken());
    }

    @PostMapping("/refresh")
    public AuthRequests.AuthResponse refresh(@RequestBody AuthRequests.RefreshRequest body) {
        Optional<RefreshToken> rt = refreshTokenService.findByToken(body.refreshToken);
        if (rt.isEmpty() || refreshTokenService.isExpired(rt.get())) {
            return new AuthRequests.AuthResponse("", "");
        }
        UserEntity user = rt.get().getUser();
        var roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        String newAccessToken = jwtUtil.generateToken(user.getUsername(), roles);
        // Optionally rotate refresh token: create new and delete old
        RefreshToken newRt = refreshTokenService.createRefreshToken(user);
        // delete old
        // For simplicity, we won't delete the old token automatically here but it is recommended
        return new AuthRequests.AuthResponse(newAccessToken, newRt.getToken());
    }

    @PostMapping("/logout")
    public String logout(@RequestBody AuthRequests.LogoutRequest body) {
        Optional<RefreshToken> rt = refreshTokenService.findByToken(body.refreshToken);
        if (rt.isEmpty()) return "OK";
        UserEntity user = rt.get().getUser();
        refreshTokenService.deleteByUser(user);
        return "OK";
    }
}
