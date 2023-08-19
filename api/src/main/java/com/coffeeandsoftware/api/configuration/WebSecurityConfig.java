package com.coffeeandsoftware.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

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
            "/comment/byPublication",
            "/publication/**",
            "/review/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, POST_WHITE_LIST)
            .permitAll()
            .antMatchers(HttpMethod.GET, GET_WHITE_LIST)
            .permitAll()
            .antMatchers("/admin/**")
            .hasRole("ADMIN")
            .anyRequest()
            .fullyAuthenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and().oauth2ResourceServer().jwt()
            .and()
            .and().cors().disable().csrf().disable();

        return http.build();
    }
}
