package com.cloud.publishing.web.security;

import com.cloud.publishing.common.dto.request.LoginRequest;
import com.cloud.publishing.common.dto.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class BackendAuthenticationProvider implements AuthenticationProvider {
    private final RestTemplate restTemplate;
    private static final String URL = "http://backend:8080/auth/login";
    private static final String ERROR_MESSAGE = "Ошибка аутентификации";

    public BackendAuthenticationProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        LoginRequest request = new LoginRequest(username, password);
        AuthResponse response = restTemplate.postForObject(URL, request, AuthResponse.class);
        if (response == null || response.accessToken() == null) {
            throw new AuthenticationServiceException(ERROR_MESSAGE);
        }
        List<SimpleGrantedAuthority> authorities = java.util.Collections.emptyList();
        if (response.roles() != null) {
            authorities = response.roles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
        auth.setDetails(response.accessToken());
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}