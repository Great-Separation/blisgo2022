package com.blisgo.config;

import com.blisgo.security.oauth.PrincipalOauth2UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;
import java.util.List;

@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.principalOauth2UserService = principalOauth2UserService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HandlerMappingIntrospector introspector = new HandlerMappingIntrospector();
        http.headers(headers -> headers
                .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true)
                        .preload(true)
                        .maxAgeInSeconds(31536000)
                )
        );

        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );

        http.cors(cors -> cors
                .configurationSource(corsConfigurationSource())
        );

        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        http.authorizeHttpRequests(request -> request
                .requestMatchers(
                        new MvcRequestMatcher(introspector, "/account/mypage"),
                        new MvcRequestMatcher(introspector, "/account/mypage/**")).authenticated()
                .requestMatchers(
                        new MvcRequestMatcher(introspector, "/board/edit/**"),
                        new MvcRequestMatcher(introspector, "/board/write")).authenticated()
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/account/login")
                .usernameParameter("email")
                .passwordParameter("pass")
                .defaultSuccessUrl("/")
                .failureUrl("/account/login")
                .loginProcessingUrl("/account/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
        );

        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/account/login")
                .userInfoEndpoint(endpoint -> endpoint
                        .userService(principalOauth2UserService)
                )
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        long MAX_AGE_SECS = 3600;
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setMaxAge(MAX_AGE_SECS);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}