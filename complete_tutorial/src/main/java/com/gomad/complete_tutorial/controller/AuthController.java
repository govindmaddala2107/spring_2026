package com.gomad.complete_tutorial.controller;

import com.gomad.complete_tutorial.exceptions.APIException;
import com.gomad.complete_tutorial.models.AppRole;
import com.gomad.complete_tutorial.models.Role;
import com.gomad.complete_tutorial.models.User;
import com.gomad.complete_tutorial.payload.CustomApiResponse;
import com.gomad.complete_tutorial.security.payload.LoginRequest;
import com.gomad.complete_tutorial.security.payload.SignupRequest;
import com.gomad.complete_tutorial.security.payload.UserInfoResponse;
import com.gomad.complete_tutorial.security.jwt.JwtUtils;
import com.gomad.complete_tutorial.security.services.UserDetailsImpl;
import com.gomad.complete_tutorial.service.RoleService;
import com.gomad.complete_tutorial.service.UserService;
import com.gomad.complete_tutorial.utils.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()));
        } catch (AuthenticationException ex) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        // After authentication is successful
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserInfoResponse loginResponse = new UserInfoResponse(userDetails.getId(), jwtCookie.toString(),
                userDetails.getUsername(), roles);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(loginResponse);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser() {
        SecurityContextHolder.clearContext();
        ResponseCookie jwtCookie = jwtUtils.clearJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new CustomApiResponse("User is signedout successfully", true));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existByUsername(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new CustomApiResponse("Username already taken..!", false));
        }
        if (userService.existByEMail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new CustomApiResponse("Email is already taken..!", false));
        }

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleService.findByRoleName(AppRole.USER)
                    .orElseThrow(() -> new APIException("Role not found..!"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole = roleService.findByRoleName(
                        AppRole.valueOf(role.toUpperCase())).orElseThrow(() -> new APIException("Role not found..!"));
                roles.add(userRole);
            });
        }

        user.setRoles(roles);
        User savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @GetMapping("/hello")
    @Tag(name = "Greetings", description = "Hello <Name> dummy url.")
    @Operation(summary = "Returns greetings message on the param name passed", description = "API to rest param request.")
    public String hello(@Parameter(description = "name that you wish to add to greetings response.") @RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("/get-email")
    @Tag(name = "User utils", description = "Helps in getting all details of User")
    @Operation(summary = "Get User's email", description = "This API will give authenticated User's Email.")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "200", description = "{\n" +
                    "\t\"email\": \"gomademail143@email.com\"\n" +
                    "}"),
    })
    public ResponseEntity<Map<String, String>> getEmail(){
        Map<String, String> map = new HashMap<>();
        String email = authUtil.getUserMail();
        map.put("email", email);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/get-username")
    @Tag(name = "User utils", description = "Helps in getting all details of User")
    public ResponseEntity<Map<String, String>> getUserName(){
        Map<String, String> map = new HashMap<>();
        String username = authUtil.getUserName();
        map.put("username", username);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
