package com.example.expand_apis_task.auth;

import com.example.expand_apis_task.auth.JwtAuthEntryPoint;
import com.example.expand_apis_task.auth.JwtAuthenticationFilter;
import com.example.expand_apis_task.config.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(hc->hc.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpBasic->httpBasic.authenticationEntryPoint(jwtAuthEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(conf ->
                        conf
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/h2-console").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/authenticate").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/add").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/all").authenticated()
                                .requestMatchers(HttpMethod.POST, "/products/add").authenticated()
                                .requestMatchers(HttpMethod.GET, "/products/all").authenticated()
                                .anyRequest().permitAll()
                )
                .exceptionHandling(conf -> conf.authenticationEntryPoint(jwtAuthEntryPoint))
                .exceptionHandling(conf -> conf.accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager webAuthenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
