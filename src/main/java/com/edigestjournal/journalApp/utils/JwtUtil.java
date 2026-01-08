package com.edigestjournal.journalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String KEY = "HBUEFE&buhsue$ua98725HVY$GJjvfBUBYSDEV7897983565&67&^^78uhsbdvfb";// any string more than 32 bytes

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token ,String username) {
        return !isTokenExpired(token) && extractUsername(token).equals(username);
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);

    }

    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject) // identification (unique elememt)(username here)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))//time of creation
                .expiration( new Date(System.currentTimeMillis()+1000*60*60))//5 mins(in milisecs)
                .signWith(getSigningKey())
                .compact();
    }

}
