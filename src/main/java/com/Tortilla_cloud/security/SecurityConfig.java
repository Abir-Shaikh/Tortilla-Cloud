package com.Tortilla_cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(new AntPathRequestMatcher("/api/**"))
                .authorizeHttpRequests(auth -> auth
                        // Public APIs
                        .requestMatchers("/api/tortillas", "/api/tortillas/**").permitAll()
                        .requestMatchers("/api/ingredients", "/api/ingredients/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED)))
                .httpBasic(basic -> {})
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth -> auth
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
