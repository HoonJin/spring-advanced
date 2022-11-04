package com.example.spring.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

//public class SecurityConfig extends WebSecurityConfigurerAdapter {
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated();

        CustomSecurityConfigurer customSecurityConfigurer = new CustomSecurityConfigurer();
        customSecurityConfigurer.setSecured(false);

        http.apply(customSecurityConfigurer);
        http.formLogin();
        http.httpBasic();

        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated();
//
//        http.formLogin()
//                .and()
//                .logout();
//    }
}
