package com.Tortilla_cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth -> auth
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
