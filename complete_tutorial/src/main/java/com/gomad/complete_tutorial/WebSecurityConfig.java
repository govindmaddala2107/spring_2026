package com.gomad.complete_tutorial;

import com.gomad.complete_tutorial.models.AppRole;
import com.gomad.complete_tutorial.models.Role;
import com.gomad.complete_tutorial.models.User;
import com.gomad.complete_tutorial.repositories.RoleRepository;
import com.gomad.complete_tutorial.repositories.UserRepository;
import com.gomad.complete_tutorial.security.jwt.AuthEntryPointJwt;
import com.gomad.complete_tutorial.security.jwt.AuthTokenFilter;
import com.gomad.complete_tutorial.security.services.UserDetailsServiceImpl;
import com.gomad.complete_tutorial.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /*
     * 1. Here we are registering a custom JWT authentication filter as a string
     * bean.
     * 2. So this AuthTokenFilter filter will
     * - intercept the request
     * - look for authentication header
     * - authenticate the request
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/swagger-ui.html",
                                "/api/swagger-ui/**",
                                "/api/v3/api-docs/**",
                                "/api/swagger-resources/**",
                                "/api/webjars/**")
                        .permitAll()
                        .requestMatchers("/auth/signin", "/auth/signup").permitAll()
                        .requestMatchers("/auth/**").authenticated()
                        // .requestMatchers("/public/**").permitAll()
                        // .requestMatchers("/admin/**").permitAll()
                        // .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        // Before calling UsernamePasswordAuthenticationFilter, call
        // authenticationJwtTokenFilter.
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    // return (web -> web
    // .ignoring()
    // .requestMatchers(
    // "/v2/api-docs/**",
    // "/configuration/ui",
    // "/swagger-resources/**",
    // "/configuration/security",
    // "/swagger-ui.html",
    // "/webjars/**"));
    // }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    // @Bean
    // public CommandLineRunner initData() {
    // return args -> {
    // if (roleService.findByRoleName(AppRole.USER).isEmpty()) {
    // roleRepository.save(new Role(AppRole.USER));
    // }
    //
    // if (roleService.findByRoleName(AppRole.ADMIN).isEmpty()) {
    // roleRepository.save(new Role(AppRole.ADMIN));
    // }
    // };
    // }
    // @Bean
    // CommandLineRunner testPassword(
    // UserRepository userRepository,
    // PasswordEncoder passwordEncoder) {
    //
    // return args -> {
    //
    // User user =
    // userRepository.findByUserName("gomad805523")
    // .orElse(null);
    //
    // System.out.println(
    // passwordEncoder.matches(
    // "gomad@12345",
    // user.getPassword()
    // )
    // );
    // };
    // }
}
