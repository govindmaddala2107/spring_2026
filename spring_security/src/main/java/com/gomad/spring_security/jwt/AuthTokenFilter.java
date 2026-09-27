package com.gomad.spring_security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("AuthTokenFilter called for url {}", request.getRequestURI());
        try {
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // UsernamePasswordAuthenticationToken designed for simple presentation of a username and password.
                // This helps us set the rules and permissions for this particular user.
                // getAuthorities object is controlling what all rules this user has.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // attaching all the request details to authentication object.

                // Now we have to say this request is authenticated.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // SecurityContext: Interface defining the minimum security information associated with the current thread
                // setAuthentication: Changes the currently authenticated principal, or removes the authentication
                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());
            }
        } catch (Exception e) {
            logger.error("Can't set user authentication: {} and error trace is {}", e.getMessage(), e.getStackTrace());
        }

        // now we have to tell Spring Security that we have done with our custom filter and now you can continue with your filter chain
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        logger.debug("AuthTokenFilter jwt: {}", jwt);
        return jwt;
    }
}
