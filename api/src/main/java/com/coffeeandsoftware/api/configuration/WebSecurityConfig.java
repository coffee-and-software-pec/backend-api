package com.coffeeandsoftware.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private static final String[] POST_WHITE_LIST = {
            "/user"
    };

    private static final String[] GET_WHITE_LIST = {
            "/publication/landingPublications",
            "/publication",
            "/comment/byPublication/**",
            "/publication/**",
            "/review/**",
            "/tag",
            "/tag/**",
            "/user/getByEmail/**",
            "/swagger-ui/**",
            "/",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/actuator/**"
    };

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("POST", "PUT", "PATCH", "GET", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(List.of("Authorization", "content-type", "xsrf-token", "origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
//            .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, POST_WHITE_LIST)
            .permitAll()
            .antMatchers(HttpMethod.GET, GET_WHITE_LIST)
            .permitAll()
            .antMatchers("/admin/**")
            .hasRole("ADMIN")
            .anyRequest()
            .fullyAuthenticated()
            .and().oauth2ResourceServer().jwt()
            .and()
            .and().csrf().disable()
//            .cors().configurationSource(corsConfigurationSource())
            ;

        return http.build();
    }
}
