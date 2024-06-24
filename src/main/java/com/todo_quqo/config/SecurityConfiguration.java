package com.todo_quqo.config;

import com.todo_quqo.exception.entry_point.CustomAccessDeniedEntryPoint;
import com.todo_quqo.exception.entry_point.CustomBasicAuthenticationEntryPoint;
import com.todo_quqo.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    private final AuthenticationConfiguration configuration;

    private final CustomBasicAuthenticationEntryPoint authenticationEntryPoint;

    private final CustomAccessDeniedEntryPoint accessDeniedEntryPoint;

    public SecurityConfiguration(JwtFilter jwtFilter,
                                 AuthenticationConfiguration configuration,
                                 CustomBasicAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedEntryPoint accessDeniedEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.configuration = configuration;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedEntryPoint = accessDeniedEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                        // public - auth
                        .requestMatchers(HttpMethod.POST, "/api/v0/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v0/authenticate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        // public - task
                        .requestMatchers(HttpMethod.GET, "/api/v0/public/todos").permitAll()
                        // authenticate require - task
                        .requestMatchers(HttpMethod.POST, "/api/v0/todos").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v0/todos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v0/todos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v0/todos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v0/todos/**").authenticated()

                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(HttpBasicConfigurer::disable)
                .exceptionHandling(e -> e
                        .accessDeniedHandler(accessDeniedEntryPoint)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
        ;
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }
}