package com.example.recruitmenttaskv01.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class AcceptHeaderFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String acceptHeader = httpRequest.getHeader(HttpHeaders.ACCEPT);

        if (!MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {

            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpResponse.getWriter().write("{\"status\":\"406 NOT_ACCEPTABLE\",\"message\":\"Unsupported media type. Please specify Accept: application/json in your request header.\"}");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}