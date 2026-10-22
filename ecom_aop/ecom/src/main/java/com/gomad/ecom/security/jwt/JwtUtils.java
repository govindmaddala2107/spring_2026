package com.gomad.ecom.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.springSecurity.app.jwtCookie}")
    private String jwtCookie;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    // Getting JWT From Headers
    public String getTokenFromHeaders(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) return token.substring(7);
        return null;
    }

    // Getting JWT From Cookie
    public String getTokenFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie != null) return cookie.getValue();
        return null;
    }

    // Generate JWT From username
    public String generateJwtFromUsername(UserDetails userDetails){
        String username = userDetails.getUsername();
        return Jwts.builder()
                .signWith(key())
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .compact();
    }

    // Key for encryption
    public SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Generate Token and send as Cookie
    public ResponseCookie generateAndSendTokenAsCookie(UserDetails userDetails){
        String jwt = generateJwtFromUsername(userDetails);
        return ResponseCookie.from(jwtCookie, jwt)
                .path(contextPath)
                .httpOnly(true)
                .secure(false) // true in production with HTTPS
                .maxAge(jwtExpirationMs / 1000)
                .sameSite("Lax")
                .build();
    }

    // Get Token and extract and send username:
    public String getUserNameFromJwt(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validate JWT token
    public boolean validateJWT(String token){
        try {
            logger.debug("Validation of token started for {}", token);
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            logger.debug("Error occurred during validation : {}", e.getMessage());
            return false;
        }
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, "")
                .path(contextPath)
                .maxAge(0)
                .httpOnly(true)
                .build();
    }
}
