package com.gomad.ecom.controllers;

import com.gomad.ecom.aspects.LogAspect;
import com.gomad.ecom.payload.AuthUserDetailsDTO;
import com.gomad.ecom.payload.CustomApiResponse;
import com.gomad.ecom.payload.LoginRequestDTO;
import com.gomad.ecom.payload.SignupRequestDTO;
import com.gomad.ecom.security.jwt.JwtUtils;
import com.gomad.ecom.security.services.AuthUserDetails;
import com.gomad.ecom.security.services.UserDetailsImpl;
import com.gomad.ecom.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUserDetails authUserDetails;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/sign-up")
    public ResponseEntity<CustomApiResponse> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        return new ResponseEntity<>(userService.registerUser(signupRequestDTO), HttpStatus.OK);
    }

    @LogAspect
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequestDTO loginRequest) {
        boolean isUserSignedIn = userService.signIn(loginRequest);
        if (isUserSignedIn) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assert authentication != null;
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (userDetails == null) {
                return new ResponseEntity<>(new CustomApiResponse("Bad Credentials", false, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }
            ResponseCookie cookie = jwtUtils.generateAndSendTokenAsCookie(userDetails);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

            return new ResponseEntity<>(new CustomApiResponse("User login successful", true, HttpStatus.OK), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new CustomApiResponse("Bad Credentials", false, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/userdetails")
    public ResponseEntity<?> getUserDetails() {
        AuthUserDetailsDTO authUser = authUserDetails.getUserDetails();
        if (authUser == null) {
            return new ResponseEntity<>(new CustomApiResponse("No user details found.", false, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authUser, HttpStatus.OK);
    }

}
