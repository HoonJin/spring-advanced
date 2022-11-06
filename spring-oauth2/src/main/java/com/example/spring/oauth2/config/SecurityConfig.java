package com.example.spring.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
//        http.formLogin();
        http.httpBasic()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.exceptionHandling().authenticationEntryPoint(
//                // AuthenticationEntryPoint 인터페이스 구현체 참조
//                // DelegatingAuthenticationEntryPoint 가 상황에 맞는 예외 entryPoint 를 찾아서 적절한 에러를 줌
//                (request, response, authException) -> {
//                    System.out.println("request = " + request);
//                    System.out.println("authException = " + authException);
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//                });

        return http.build();
    }

    static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.addHeader("WWW-Authenticate", "Basic realm=\"localhost\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
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
