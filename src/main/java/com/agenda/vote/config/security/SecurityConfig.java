package com.agenda.vote.config.security;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {


//    @Bean
//    public OAuth2LoginConfigurer<HttpSecurity> filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests()
//                .mvcMatchers("/api/v1/admin").hasRole(Role.ADMIN.getName())
//                .anyRequest().permitAll()
//                .and().oauth2Login();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.oauth2Login()
                .and().build();
    }
}