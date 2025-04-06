package com.practice.practicemanage.security;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.service.impl.UserServiceImpl;
import com.practice.practicemanage.utils.JwtUtil;
import com.practice.practicemanage.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

//继承 OncePerRequestFilter：确保每个请求只调用一次该过滤器，适合进行请求预处理。
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RedisUtil redisUtil;


//    拦截器
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        String refreshToken = getRefreshTokenFromRequest(request);
        System.out.println("请求方法 Request method: " + request.getMethod());  // 打印请求方法
//        System.out.println("token Request token: " + token);  // 打印请求方法

        // 跳过登录和注册请求，不进行JWT验证
        if (request.getRequestURI().equals("/login") || request.getRequestURI().equals("/register") || request.getRequestURI().equals("/getScoolORUnitList")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (token != null && jwtUtil.isTokenValid(token) && Objects.equals(userService.getUserByToken("TOKEN_",token).getUserName(), jwtUtil.extractUsername(token))){
//          如果token有效，设置用户信息到SecurityContext
            System.out.println("令牌未过期，刷新令牌未过期，或正在第一次登录");
            UserDetails userDetails = userService.loadUserByUsername("TOKEN_", token, jwtUtil.extractUsername(token));
//          创建一个 UsernamePasswordAuthenticationToken 对象，该对象表示一个已认证的用户身份，包含了认证所需的核心信息。
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//          将创建的 authentication 对象（即用户的认证信息）存入 SecurityContextHolder 的 SecurityContext 中。
//          SecurityContextHolder.getContext()：这是 Spring Security 提供的一个上下文对象，用来保存当前线程中的用户认证信息。
//          setAuthentication(authentication)：通过 setAuthentication 方法将当前的 authentication（即用户的认证信息）设置到上下文中，表示当前用户已经通过认证。
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (refreshToken != null && jwtUtil.isTokenValid(refreshToken) && Objects.equals(userService.getUserByToken("REFRESHTOKEN_", refreshToken).getUserName(), jwtUtil.extractUsername(refreshToken))){
//            令牌过期，刷新令牌未过期，生成新令牌
            System.out.println("令牌过期，刷新令牌未过期，生成新令牌");
            UserDetails userDetails = userService.loadUserByUsername("REFRESHTOKEN_", refreshToken, jwtUtil.extractUsername(refreshToken));
            User refreshtoken = userService.getUserByToken("REFRESHTOKEN_", refreshToken);
            String newToken = jwtUtil.createToken(refreshtoken.getUserName());
            response.setHeader("Authorizationagain", newToken);
            redisUtil.set("TOKEN_"+newToken+refreshtoken.getUserName(), refreshtoken, 1, TimeUnit.HOURS); // 1小时过期
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

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
