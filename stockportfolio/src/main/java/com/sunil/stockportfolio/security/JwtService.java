package com.sunil.stockportfolio.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
     String SECRET = "EY7Q6h3zoTl9FNOh7ZgBB8Io031GOlfYGq9aXfQhtqj";
    public String generateJwtToken(String email) {
       return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .addClaims(new HashMap<>())
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    public boolean validateToken(String token) throws Exception{
        Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token);
        return true;
    }
    public String extractEmail(String token) throws  Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
