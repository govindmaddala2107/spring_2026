package com.gomad.ecom.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String token = getJwtToken(request);

            if (token != null &&
                    !token.isBlank() &&
                    jwtUtils.validateJWT(token)) {

                String username =
                        jwtUtils.getUserNameFromJwt(token);

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (UsernameNotFoundException ex) {

            logger.error("User not found: {}", ex.getMessage());

            SecurityContextHolder.clearContext();

            response.addHeader(
                    HttpHeaders.SET_COOKIE,
                    jwtUtils.getCleanJwtCookie().toString()
            );

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                      "message":"%s",
                      "status":false,
                      "code":"401 UNAUTHORIZED"
                    }
                    """.formatted(ex.getMessage()));

            return;
        }

        filterChain.doFilter(request, response);
    }

    public String getJwtToken(HttpServletRequest request) {
        String tokenFromHeader = jwtUtils.getTokenFromHeaders(request);
        if (tokenFromHeader != null) return tokenFromHeader;
        return jwtUtils.getTokenFromCookie(request);
    }
}
