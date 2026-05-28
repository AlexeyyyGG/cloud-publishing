package com.cloud.publishing.backend.controller;

import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.common.dto.request.LoginRequest;
import com.cloud.publishing.common.dto.request.RefreshRequest;
import com.cloud.publishing.common.dto.response.AuthResponse;
import com.cloud.publishing.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Urls.AUTH)
public class AuthController {
    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(Urls.LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping(Urls.REFRESH)
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(service.refreshToken(request.refreshToken()));
    }
}