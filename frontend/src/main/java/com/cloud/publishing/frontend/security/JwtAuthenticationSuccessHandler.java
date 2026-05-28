package com.cloud.publishing.frontend.security;

import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.common.dto.response.AuthResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    public JwtAuthenticationSuccessHandler() {
        setDefaultTargetUrl(Urls.WEB_EMPLOYEES);
        setAlwaysUseDefaultTargetUrl(false);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        AuthResponse authResponse = (AuthResponse) authentication.getDetails();
        HttpSession session = request.getSession();
        session.setAttribute(ACCESS_TOKEN, authResponse.accessToken());
        session.setAttribute(REFRESH_TOKEN, authResponse.refreshToken());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}