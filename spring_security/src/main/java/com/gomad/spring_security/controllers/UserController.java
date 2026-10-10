package com.gomad.spring_security.controllers;

import com.gomad.spring_security.jwt.JwtUtils;
import com.gomad.spring_security.payload.LoginRequest;
import com.gomad.spring_security.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    /*
    AuthenticationManager:
    1. AuthenticationManager implements Authentication.
    2. Authentication processes an Authentication request and returning a fully populated Authentication object
    (including granted authorities) if successful.

    UsernamePasswordAuthenticationToken:
    1. UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken and
    AbstractAuthenticationToken implements Authentication, CredentialsContainer. So UsernamePasswordAuthenticationToken implements
    Authentication at the end.
    2. UsernamePasswordAuthenticationToken is Authentication implementation that is designed for simple presentation of
    a username and password.

    SecurityContextHolder
    1. Associates a given SecurityContext} with the current execution thread.
    2. The purpose of the class is to provide a convenient way to specify the strategy that should be used for a given
    JVM. This is a JVM-wide setting, since everything in this class is static to facilitate ease of use in calling code.
    3. getContext(): Obtains and return the security context.
    4. setAuthentication(): Changes the currently authenticated principal [user], or removes the authentication information.


    */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndpoint() {
        return "Hello User..!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Hello Admin..!";
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }catch(AuthenticationException ex){
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        // After authentication is successful
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails == null){
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
//        String token = jwtUtils.generateTokenFromUsername(userDetails);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
//                .map(item -> item.getAuthority())
                .toList();
        LoginResponse loginResponse = new LoginResponse(jwtCookie.toString(), userDetails.getUsername(), roles);
//        return ResponseEntity.ok(loginResponse);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
                .body(loginResponse);
    }
}
