package com.practice.practicemanage.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    private static final long JWT_EXPIRATION = 60 * 60 * 1000;  // 1小时的有效期
    private static final long JWT_REFRESH_EXPIRATION = 60 * 60 * 1000 * 24 * 7;  // 7天的有效期，适用于刷新令牌

    @Autowired
    private LogUtil logUtil;

    // 生成访问令牌（JWT）
    public String createToken(String username) {
        logUtil.info(JwtUtil.class, "正在为用户生成JWT令牌: {}", username);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);  // 过期时间

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        logUtil.info(JwtUtil.class, "JWT令牌已成功为用户生成: {}", username);
        return token;
    }

    // 生成刷新令牌（Refresh Token）
    public String createRefreshToken(String username) {
        logUtil.info(JwtUtil.class, "正在为用户生成刷新令牌: {}", username);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_REFRESH_EXPIRATION);  // 刷新令牌的过期时间更长

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        logUtil.info(JwtUtil.class, "刷新令牌已成功为用户生成: {}", username);
        return token;
    }

    // 验证JWT令牌并返回Claims
    public Claims validateToken(String token) {
        logUtil.info(JwtUtil.class, "正在验证JWT令牌: {}", token);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)  // 使用parseClaimsJws代替parseClaimsJwt
                    .getBody();
            logUtil.info(JwtUtil.class, "JWT令牌验证成功: {}", token);
            return claims;
        } catch (ExpiredJwtException e) {
            logUtil.error(JwtUtil.class, "JWT令牌已过期: {}", token, e);
        } catch (UnsupportedJwtException e) {
            logUtil.error(JwtUtil.class, "不支持的JWT令牌: {}", token, e);
        } catch (MalformedJwtException e) {
            logUtil.error(JwtUtil.class, "JWT令牌格式错误: {}", token, e);
        } catch (SignatureException e) {
            logUtil.error(JwtUtil.class, "JWT令牌签名无效: {}", token, e);
        } catch (IllegalArgumentException e) {
            logUtil.error(JwtUtil.class, "JWT令牌为空或无效: {}", token, e);
        }
        return null;
    }

    // 从JWT令牌中提取用户名
    public String extractUsername(String token) {
        Claims claims = validateToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        logUtil.error(JwtUtil.class, "提取JWT令牌中的用户名失败: {}", token);
        return null;
    }

    // 检查JWT令牌是否过期
    public boolean isTokenExpired(String token) {
        Claims claims = validateToken(token);
        if (claims != null) {
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        }
        return true;  // 如果验证失败，默认认为过期
    }

    // 检查JWT令牌是否有效
    public boolean isTokenValid(String token) {
        return token != null && !isTokenExpired(token);
    }
}
