package com.example.app.service;

import com.example.app.model.RefreshToken;
import com.example.app.model.UserEntity;
import com.example.app.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

/**
 * Very simple refresh token service. For production, consider rotating tokens, storing hashes of tokens,
 * and additional device/session tracking.
 */
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    // default expiry 7 days; configurable via env in production
    private final long refreshTokenDurationDays = Long.parseLong(System.getenv().getOrDefault("REFRESH_TOKEN_DAYS","7"));

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(UserEntity user) {
        String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        Instant expiry = Instant.now().plus(refreshTokenDurationDays, ChronoUnit.DAYS);
        RefreshToken rt = new RefreshToken(token, user, expiry);
        return refreshTokenRepository.save(rt);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    public int deleteByUser(UserEntity user) {
        return refreshTokenRepository.deleteByUser(user);
    }
}
