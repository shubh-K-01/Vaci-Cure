package com.demeatrix.VaciCure.config;

import com.demeatrix.VaciCure.security.AuthFilter;
import com.demeatrix.VaciCure.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthFilter authFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/verify/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Vaccine catalog
                        .requestMatchers(HttpMethod.GET, "/vaccines/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN", "PHARMACIST")
                        .requestMatchers(HttpMethod.POST, "/vaccines/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vaccines/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vaccines/**").hasRole("ADMIN")

                        // Doctor and nurse endpoints
                        .requestMatchers("/vaccinations/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/profile/doctors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/inventory/**").hasAnyRole("PHARMACIST", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/inventory/**").hasAnyRole("PHARMACIST", "ADMIN", "DOCTOR", "NURSE")

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
