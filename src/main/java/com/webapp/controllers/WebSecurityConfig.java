package com.webapp.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/static/**").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/signup", "/signup/**").permitAll()
        .and()
        .csrf()
        .disable()
        .formLogin()
        .and()
        .logout()
        .permitAll();
  }

  @Bean
  PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}