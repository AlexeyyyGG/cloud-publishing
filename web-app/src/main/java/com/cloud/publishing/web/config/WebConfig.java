package com.cloud.publishing.web.config;

import com.cloud.publishing.web.security.BackendAuthenticationProvider;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.cloud.publishing.web.controller",
        "com.cloud.publishing.web.client",
        "com.cloud.publishing.web.mapper"
})
public class WebConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((
                request,
                body,
                execution) -> {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) {
                return execution.execute(request, body);
            }
            HttpSession session = attrs.getRequest().getSession(false);
            if (session == null) {
                return execution.execute(request, body);
            }
            String token = (String) session.getAttribute("access_token");
            if (token == null || token.isBlank()) {
                return execution.execute(request, body);
            }
            request.getHeaders().setBearerAuth(token);
            return execution.execute(request, body);
        }));
        return restTemplate;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(RestTemplate restTemplate) {
        return new BackendAuthenticationProvider(restTemplate);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JwtAuthenticationSuccessHandler();
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}