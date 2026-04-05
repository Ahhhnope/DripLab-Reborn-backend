package com.example.cafe.Config;

import com.example.cafe.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Public endpoints - no login needed
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/drinks/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/sizes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/toppings/**").permitAll()
                .requestMatchers("/IMG/**").permitAll()

                // Admin-only endpoints
                .requestMatchers("/api/ingredients/**").hasRole("ADMIN")
                .requestMatchers("/api/instructions/**").hasRole("ADMIN")
                .requestMatchers("/api/workers/**").hasRole("ADMIN")
                .requestMatchers("/api/dashboard/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/drinks/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/drinks/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/drinks/**").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasRole("ADMIN")

                // Any authenticated user (USER or ADMIN)
                .requestMatchers("/api/cart/**").authenticated()
                .requestMatchers("/api/orders/**").authenticated()
                .requestMatchers("/api/customers/**").authenticated()
                .requestMatchers("/api/invoices/**").authenticated()

                // Everything else requires auth
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
