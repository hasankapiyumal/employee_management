package com.hasanka.emloyee_management.auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/employee/*")
public class AuthFilter implements Filter {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String username = httpRequest.getHeader("Username");
        String password = httpRequest.getHeader("Password");
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {

            if (USERNAME.equals(username) && PASSWORD.equals(password)) {

                filterChain.doFilter(servletRequest, servletResponse);
            } else {

                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unauthorized: Invalid username or password");
            }
        } else {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
