package com.campusactivity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/login", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/user/activities", "/user/activities/**").permitAll()
                .antMatchers("/h2-console/**").permitAll() // 允许访问H2控制台
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .csrf().disable() // 暂时禁用CSRF以便测试
            .headers().frameOptions().disable(); // 允许H2控制台的iframe

        return http.build();
    }
}