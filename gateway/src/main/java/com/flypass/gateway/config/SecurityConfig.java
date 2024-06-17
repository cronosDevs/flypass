package com.flypass.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method sets up the security configuration for HTTP requests. It allows all requests to be
     * permitted without authentication, configures CORS settings, and disables CSRF protection.
     *
     * @param http the {@link HttpSecurity} to modify.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs while configuring the {@link HttpSecurity}.
     */
    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable();

        return http.build();

    }

    /**
     * Configures the CORS settings for the application.
     * <p>
     * This method creates a {@link CorsConfiguration} object and sets up the allowed origins, methods,
     * and headers for CORS. It then registers this configuration with a {@link UrlBasedCorsConfigurationSource}.
     *
     * @return a {@link CorsConfigurationSource} with the configured CORS settings.
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(List.of("GET", "HEAD", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
