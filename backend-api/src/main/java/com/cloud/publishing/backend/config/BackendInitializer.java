package com.cloud.publishing.backend.config;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class BackendInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{BackendConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    @NonNull
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }
}