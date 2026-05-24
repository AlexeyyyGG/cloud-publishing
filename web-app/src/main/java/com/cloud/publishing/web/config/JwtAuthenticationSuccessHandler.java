package com.cloud.publishing.web.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response,
            Authentication authentication
    ) throws java.io.IOException {
        String token = (String) authentication.getDetails();
        HttpSession session = request.getSession();
        session.setAttribute("access_token", token);
        response.sendRedirect("/web/employees");
    }
}
