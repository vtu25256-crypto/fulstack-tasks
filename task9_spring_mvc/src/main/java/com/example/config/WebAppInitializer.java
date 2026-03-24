package com.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null; // Root config (Services/Persistence)
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class }; // MVC Config
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // Map everything to DispatcherServlet
    }
}
