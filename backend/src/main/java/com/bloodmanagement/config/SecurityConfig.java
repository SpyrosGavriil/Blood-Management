package com.bloodmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOrigin("http://127.0.0.1:5500"); // Allow your frontend URL
                configuration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)
                configuration.addAllowedHeader("*"); // Allow all headers
                configuration.setAllowCredentials(true); // Allow cookies or credentials

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/admins/**").hasRole("ADMIN")
                                                .requestMatchers("/api/donors/**").hasRole("ADMIN")
                                                .requestMatchers("/api/blood-banks/getAll").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/api/blood-banks/**").hasRole("ADMIN")
                                                .requestMatchers("/api/donation-records/get/{id}").permitAll()
                                                .requestMatchers("/api/donation-records/donor/{politicalId}").permitAll()
                                                .requestMatchers("/api/donation-records/**").hasRole("ADMIN")
                                                .requestMatchers("/api/users/get/{id}").permitAll()
                                                .requestMatchers("/api/users/**").hasAnyRole("ADMIN")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

}
