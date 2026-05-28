package com.cloud.publishing.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain webSecurityFilterChain(
            HttpSecurity http,
            AuthenticationProvider provider,
            AuthenticationSuccessHandler successHandler
    ) throws Exception {
        http
                .authenticationProvider(provider)
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/web/**").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((
                                request,
                                response,
                                accessDeniedException) ->
                                request.getRequestDispatcher("/WEB-INF/views/error/403.jsp")
                                        .forward(request, response)))
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession())
                .formLogin(form -> form
                        .successHandler(successHandler))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );
        return http.build();
    }
}