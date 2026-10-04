package com.gomad.complete_tutorial.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    // Getting JWT From Headers
//    public String getJwtFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Bearer Token: {}", bearerToken);
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); // Remove Bearer and return only token.
//        }
//        logger.debug("Bearer Token is empty");
//        return null;
//    }

    // get cookie from cookies
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        assert cookie != null;
        return cookie.getValue() != null ? cookie.getValue() : null;
    }

    // generate cookie and send as response
    public ResponseCookie generateJwtCookie(UserDetails userDetails){
        String jwt = generateTokenFromUsername(userDetails);
        return ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(jwtExpirationMs / 1000)
                .httpOnly(false)
                .build();
    }

    // Generating Token from Username
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key()) // we are signing the token using a secret key. We need to sign the token with
                // the secret key so that it is proved that token is legit and wasn't tampered
                // with.
                .compact(); // which means it finishes the building and returns the token in a compact
        // string format.
    }

    // Getting Username from JWT token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser() // start to build the parser to read JWT
                .verifyWith(key())// then verify using the key
                .build()// to build the parser
                .parseSignedClaims(token)// parse the JWT and extract its claims. Claims means the data inside that we
                // will add during token creation.
                .getPayload() // to get actual content or payload of the token
                .getSubject(); // to get the subject field from the payload.
    }

    // Generate Signing Key
    public SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Validate JWT Token
    public boolean validateJwtToken(String token) {
        try {
            logger.debug("Validating jwt token...");
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT is expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature {}", e.getMessage());
        } catch (JwtException e) {
            logger.error("JWT validation failed {}", e.getMessage());
        }
        return false;
    }
}
