package com.cloud.publishing.frontend.security;

import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.common.dto.request.LoginRequest;
import com.cloud.publishing.common.dto.response.AuthResponse;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class BackendAuthenticationProvider implements AuthenticationProvider {
    private final RestTemplate restTemplate;
    private final String backendUrl;
    private static final String ERROR_MESSAGE = "Ошибка аутентификации";

    public BackendAuthenticationProvider(
            @Qualifier("authRestTemplate") RestTemplate restTemplate,
            @Value("${backend.url}") String backendUrl
    ) {
        this.restTemplate = restTemplate;
        this.backendUrl = backendUrl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        LoginRequest request = new LoginRequest(username, password);
        String url = backendUrl + Urls.AUTH + Urls.LOGIN;
        AuthResponse response = restTemplate.postForObject(url, request, AuthResponse.class);
        if (response == null || response.accessToken() == null) {
            throw new AuthenticationServiceException(ERROR_MESSAGE);
        }
        List<GrantedAuthority> authorities = response.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
        auth.setDetails(response);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}