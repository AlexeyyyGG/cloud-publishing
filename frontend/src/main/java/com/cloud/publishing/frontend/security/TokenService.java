package com.cloud.publishing.frontend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.common.dto.request.RefreshRequest;
import com.cloud.publishing.common.dto.response.AuthResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    private final RestTemplate restTemplate;
    private final String backendUrl;
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String SESSION_EXPIRED_MSG = "Сессия истекла";
    private static final String INVALID_TOKEN_MSG = "Некорректный JWT токен";

    public TokenService(
            @Qualifier("authRestTemplate") RestTemplate restTemplate,
            @Value("${backend.url}") String backendUrl
    ) {
        this.restTemplate = restTemplate;
        this.backendUrl = backendUrl;
    }

    public String getValidAccessToken(HttpSession session) {
        String token = (String) session.getAttribute(ACCESS_TOKEN);
        if (token != null && !isExpired(token)) {
            return token;
        }
        return refreshToken(session);
    }

    private boolean isExpired(String token) {
        try {
            Date expiresAt = JWT.decode(token).getExpiresAt();
            return expiresAt != null && expiresAt.before(new Date());
        } catch (JWTDecodeException e) {
            throw new BadCredentialsException(INVALID_TOKEN_MSG, e);
        }
    }

    private String refreshToken(HttpSession session) {
        String token = (String) session.getAttribute(REFRESH_TOKEN);
        String url = backendUrl + Urls.AUTH + Urls.REFRESH;
        AuthResponse response = restTemplate.postForObject(
                url,
                new RefreshRequest(token),
                AuthResponse.class
        );
        if (response == null || response.accessToken() == null) {
            session.invalidate();
            throw new InsufficientAuthenticationException(SESSION_EXPIRED_MSG);
        }
        session.setAttribute(ACCESS_TOKEN, response.accessToken());
        session.setAttribute(REFRESH_TOKEN, response.refreshToken());
        return response.accessToken();
    }
}