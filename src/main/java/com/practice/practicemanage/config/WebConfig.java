package com.practice.practicemanage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9527")  // 允许的前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的 HTTP 方法
                .allowedHeaders("Authorization", "RefreshAuthorization", "Content-Type", "X-Requested-With", "Accept", "Origin")  // 允许的请求头
                .allowCredentials(true)  // 允许携带凭证
                .maxAge(3600);  // 设置缓存时间（单位：秒）
    }
}