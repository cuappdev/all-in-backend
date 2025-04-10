package com.appdev.allin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private final FirebaseTokenFilter firebaseTokenFilter;

  public SecurityConfig(FirebaseTokenFilter firebaseTokenFilter) {
    this.firebaseTokenFilter = firebaseTokenFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        // .antMatchers("/public/**").permitAll() // Uncomment to allow public endpoints
        .anyRequest().authenticated() // Require authentication for all other endpoints
        .and()
        .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}