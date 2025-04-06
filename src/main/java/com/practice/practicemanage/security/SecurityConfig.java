package com.practice.practicemanage.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.LoginServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // 该注解启用 Spring Security 的 web 安全功能。
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private LoginServiceImpl loginService;

    @Bean
    public PasswordEncoder passwordEncoder() {
//        使用 BCryptPasswordEncoder 加密密码
//        也可用有参构造，取值范围是 4 到 31，默认值为 10。数值越大，加密计算越复杂，安全性越高，但是性能会降低。
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // 获取 AuthenticationManager 对象
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable) // 基于token，不需要csrf
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 基于token，不需要session
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/login",  "/register", "/getScoolORUnitList").permitAll() // 放行登录和注册请求
                        .requestMatchers("/adm/**").hasRole("ADMINISTRATOR") // 需要 ADMIN 角色
                        .requestMatchers(HttpMethod.OPTIONS).permitAll() // 放行跨域请求的预检请求
                        .anyRequest().authenticated() // 其他请求都需要认证
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            loginService.logouts(getTokenFromRequest(request), getRefreshTokenFromRequest(request));
                            // 设置响应头 Content-Type 为 application/json
                            // 设置响应头
                            response.setContentType("application/json;charset=UTF-8");
                            response.setHeader("Access-Control-Allow-Origin", "http://localhost:9528"); // 允许的跨域来源
                            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
                            response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许携带凭证（如 cookies）

                            // 发送返回的响应内容
                            ResponseMessage<Object> responseMessage = ResponseMessage.success("登出成功");
                            response.getWriter().write(new ObjectMapper().writeValueAsString(responseMessage));  // 序列化为 JSON 格式

                            // 确保返回状态码 200
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .clearAuthentication(true)  // 清除认证信息
                        .invalidateHttpSession(true) // 使会话无效
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("RefreshAuthorization");
        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
