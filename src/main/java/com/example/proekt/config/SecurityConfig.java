
package com.example.proekt.config;

import com.example.proekt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Autowired
        private UserServiceImpl userService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/register", "/registration-success", "/h2-console/**").permitAll()
                            .requestMatchers("/css/**", "/style.css", "/images/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .permitAll()
                    )
                    .headers(headers -> headers.disable())
                    .userDetailsService(userService);  // Injecting UserServiceImpl as UserDetailsService

            return http.build();
        }


    }

