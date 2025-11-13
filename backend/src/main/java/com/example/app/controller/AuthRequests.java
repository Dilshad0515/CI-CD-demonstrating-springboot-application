package com.example.app.controller;

import java.util.Set;

/**
 * DTOs for auth requests/responses.
 */
public class AuthRequests {
    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class RegisterRequest {
        public String username;
        public String password;
        public Set<String> roles;
    }

    public static class RefreshRequest {
        public String refreshToken;
    }

    public static class LogoutRequest {
        public String refreshToken;
    }

    public static class AuthResponse {
        public String accessToken;
        public String refreshToken;
        public String tokenType = "Bearer";
        public AuthResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
