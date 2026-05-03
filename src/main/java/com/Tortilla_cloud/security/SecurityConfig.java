package com.Tortilla_cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        // Public APIs
                        .requestMatchers("/api/tortillas/**").permitAll()
                        .requestMatchers("/api/ingredients/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED)))
                .httpBasic(basic -> {})
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception{
        http
                .securityMatcher(request -> !request.getRequestURI().startsWith("/api/"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/images/**", "/menu/**", "/buttons/**", "/css/**", "/js/**", "/webjars/**", "/favicon.ico").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/" , "/login" , "/register").permitAll()
                        .requestMatchers("/design" , "/orders/**").authenticated()
                        .requestMatchers("/design", "/orders/**", "/orders").authenticated()
                        .anyRequest().authenticated())

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll())


                .formLogin(
                        login -> login
                                .loginPage("/login")
                                .defaultSuccessUrl("/design" , false)
                                .permitAll())

                .csrf( csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**"))

                .headers(headers->headers
                        .frameOptions(frame-> frame.disable())
                );
        return http.build();
    }
}
