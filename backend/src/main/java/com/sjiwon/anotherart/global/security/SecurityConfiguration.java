package com.sjiwon.anotherart.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjiwon.anotherart.global.security.filter.AjaxAuthenticationFilter;
import com.sjiwon.anotherart.global.security.filter.JwtAuthorizationFilter;
import com.sjiwon.anotherart.global.security.filter.LogoutExceptionTranslationFilter;
import com.sjiwon.anotherart.global.security.filter.TokenInvalidExceptionTranslationFilter;
import com.sjiwon.anotherart.global.security.handler.AjaxAuthenticationFailureHandler;
import com.sjiwon.anotherart.global.security.handler.AjaxAuthenticationSuccessHandler;
import com.sjiwon.anotherart.global.security.handler.JwtAccessDeniedHandler;
import com.sjiwon.anotherart.global.security.handler.JwtAuthenticationEntryPoint;
import com.sjiwon.anotherart.global.security.handler.JwtLogoutSuccessHandler;
import com.sjiwon.anotherart.global.security.provider.AjaxAuthenticationProvider;
import com.sjiwon.anotherart.global.security.service.CustomUserDetailsService;
import com.sjiwon.anotherart.member.domain.MemberRepository;
import com.sjiwon.anotherart.token.service.TokenManager;
import com.sjiwon.anotherart.token.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final TokenManager tokenManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", corsConfiguration);
        return source;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(memberRepository);
    }

    @Bean
    AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider(userDetailsService(), passwordEncoder());
    }

    @Bean
    AuthenticationManager ajaxAuthenticationManager() throws Exception {
        final ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(ajaxAuthenticationProvider());
        return authenticationManager;
    }

    @Bean
    AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler(tokenProvider, tokenManager, objectMapper);
    }

    @Bean
    AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        final AjaxAuthenticationFilter authenticationFilter = new AjaxAuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationManager(ajaxAuthenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        return authenticationFilter;
    }

    @Bean
    JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(tokenProvider, memberRepository);
    }

    @Bean
    AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    AccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler(objectMapper);
    }

    @Bean
    TokenInvalidExceptionTranslationFilter tokenInvalidExceptionTranslationFilter() {
        return new TokenInvalidExceptionTranslationFilter(jwtAccessDeniedHandler());
    }

    @Bean
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler() {
        return new JwtLogoutSuccessHandler(tokenProvider, tokenManager);
    }

    @Bean
    LogoutExceptionTranslationFilter logoutExceptionTranslationFilter() {
        return new LogoutExceptionTranslationFilter(jwtAccessDeniedHandler());
    }

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(logoutExceptionTranslationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthorizationFilter(), LogoutExceptionTranslationFilter.class);
        http.addFilterBefore(tokenInvalidExceptionTranslationFilter(), JwtAuthorizationFilter.class);
        http.addFilterAt(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.logout(logout ->
                logout.logoutUrl("/api/logout")
                        .clearAuthentication(true)
                        .logoutSuccessHandler(jwtLogoutSuccessHandler())
        );

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint())
                        .accessDeniedHandler(jwtAccessDeniedHandler())
        );

        return http.build();
    }
}
