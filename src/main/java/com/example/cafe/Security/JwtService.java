package com.example.cafe.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String secret = "DripLab_Super_Secret_Key_9999_Make_It_Longer_For_Security";
    private static final long timeLimit = 86400000L;

    private SecretKey getSignInKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        return Jwts.builder().subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + timeLimit))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateTokenWithClaims(Map<String, Object> claims, String email) {
        return Jwts.builder().claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + timeLimit))
                .signWith(getSignInKey())
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return getClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
