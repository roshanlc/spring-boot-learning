package com.roshan.taskmgmt.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Component
public class RequestLogger implements ServletRequestListener {

    private final Logger logger = LoggerFactory.getLogger(CommonsRequestLoggingFilter.class);

    @Override
    public void requestInitialized(@NonNull ServletRequestEvent sre) {
        HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
        logger.info("Method: {} , Path: {}", httpRequest.getMethod(), httpRequest.getRequestURI());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // Nothing to do here
    }
}
