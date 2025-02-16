//package com.practice.practicemanage.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()  // 禁用 CSRF，因为我们使用 JWT，不需要 session
//                .authorizeRequests()
//                .antMatchers("/auth/login", "/auth/register").permitAll()  // 开放登录和注册接口
//                .anyRequest().authenticated();  // 其他接口需要认证
//    }
//}
