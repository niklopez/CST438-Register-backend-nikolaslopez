package com.cst438.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
    private static final String SECRET_KEY = "your-secret-key-here"; // Replace with your secret key
    private static final String PREFIX = "Bearer ";
    private static final long EXPIRATIONTIME = 864_000_000; // 10 days in milliseconds

    private static final Key key = new SecretKeySpec(DatatypeConverter.parseBase64Binary(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

    public String getToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();
        return token;
    }

    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith(PREFIX)) {
            try {
                String user = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token.replace(PREFIX, ""))
                        .getBody()
                        .getSubject();
                
                if (user != null) {
                    return user;
                }
            } catch (Exception e) {
                // Handle invalid token or other exceptions here
                e.printStackTrace(); // Log the exception
            }
        }

        return null;
    }
}
