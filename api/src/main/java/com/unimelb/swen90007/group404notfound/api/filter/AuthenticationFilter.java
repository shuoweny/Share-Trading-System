package com.unimelb.swen90007.group404notfound.api.filter;


import com.unimelb.swen90007.group404notfound.api.annotation.*;
import com.unimelb.swen90007.group404notfound.api.domain.User;
import com.unimelb.swen90007.group404notfound.api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.unimelb.swen90007.group404notfound.api.servlet.MethodManagement.getMethodByURI;
import static com.unimelb.swen90007.group404notfound.api.util.JwtUtil.parseJWT;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("EncodeFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        Method targetMethod = getMethodByURI(requestURI);

        // Get JWT token from the header of http
        String token = request.getHeader("Authorization");

        if (targetMethod.isAnnotationPresent(LoginToken.class)) {
            // if existing @LoginToken, check if the user did not log in
            if (token != null && !token.isEmpty()) {
                // if jwt token is not empty
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The user has already login");
                return;
            }
        } else if (targetMethod.isAnnotationPresent(NormalToken.class)) {
            // if existing @NormalToken，check whether the user has the token
            if (token == null || token.isEmpty()) {
                // if jwt token is empty
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing");
                return;
            }

            boolean isValid = JwtUtil.isVerify(token);
            if (!isValid) {
                // token is invalid
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                System.out.println("Invalid token");
                return;
            }

        }

        // Authorization check here

        if (targetMethod.isAnnotationPresent(RolesAllowed.class)) {
            Claims claims = parseJWT(token);
            // get userType from the token
            String userTypeInToken = claims.get("userType", String.class);

            RolesAllowed rolesAllowed = targetMethod.getAnnotation(RolesAllowed.class);
            String[] allowedRoles = rolesAllowed.value();

            boolean isAuthorized = false;
            for (String role : allowedRoles) {
                System.out.println(role);
                System.out.println(userTypeInToken);
                if (userTypeInToken.equals(role)) {
                    isAuthorized = true;
                    break;
                }
            }

            if (!isAuthorized) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        }

        // token pass
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("Authentication Filter destroy");
    }
}
