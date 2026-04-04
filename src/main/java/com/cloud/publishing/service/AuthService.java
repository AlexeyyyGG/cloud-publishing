package com.cloud.publishing.service;

import com.cloud.publishing.dto.request.LoginRequest;
import com.cloud.publishing.dto.response.AuthResponse;
import com.cloud.publishing.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsernameFromRefreshToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        return new AuthResponse(newAccessToken, refreshToken);
    }
}