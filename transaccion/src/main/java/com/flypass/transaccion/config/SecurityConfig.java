package com.flypass.transaccion.config;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//
//    /**
//     * Configures the security filter chain.
//     * <p>
//     * This configuration permits all requests to the login endpoint, enables CORS with a custom configuration,
//     * and disables CSRF protection.
//     * </p>
//     *
//     * @param http the {@link HttpSecurity} to modify
//     * @return the configured {@link SecurityFilterChain}
//     * @throws Exception if an error occurs while configuring the security filter chain
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/users-api/v0/users/login").permitAll())
//                .cors().configurationSource(corsConfigurationSource()).and()
//                .csrf().disable();
//
//        return http.build();
//
//    }
//
//    /**
//     * Provides the CORS configuration source.
//     * <p>
//     * This configuration allows all origins, methods, and headers.
//     * </p>
//     *
//     * @return the {@link CorsConfigurationSource} with the configured CORS settings
//     */
//    private CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedMethods(List.of("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


}
