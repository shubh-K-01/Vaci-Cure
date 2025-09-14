package com.demeatrix.VaciCure.security;

import com.demeatrix.VaciCure.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private final long accessTokenExpiration = 15 * 60 * 1000;  // 15 min
    private final long refreshTokenExpiration = 30 * 24 * 60 * 60 * 1000; // 30 days

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();  // Use getBody() instead of getPayload()

            return claims.getSubject();

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            throw new JwtException("Invalid JWT signature", e);
        } catch (ExpiredJwtException e) {
            log.error("JWT token expired");
            throw new JwtException("JWT token expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            throw new JwtException("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT token is invalid or empty");
            throw new JwtException("JWT token is invalid or empty", e);
        }
    }
}
