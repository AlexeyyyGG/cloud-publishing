package com.cloud.publishing.web.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ SecurityConfig.class, WebConfig.class };
    }

    @Override
    @NonNull
    protected String[] getServletMappings() {

        return new String[]{ "/" };
    }
    @Override
    public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }
}