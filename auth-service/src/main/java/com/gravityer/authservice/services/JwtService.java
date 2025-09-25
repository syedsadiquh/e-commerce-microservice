package com.gravityer.authservice.services;

import com.gravityer.authservice.models.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    // Dummy Secret
    private final String SECRET = "DummySecret123qwertyuiopasdfghjklzxcvbnm1234567890";

    public String generateToken(UserPrincipal userPrincipal) {

        HashMap<String, Object> allClaims = new HashMap<>();
        allClaims.put("roles", userPrincipal.getUser().getRoles());

        log.info("Generating JWT for user: {} with roles {}", userPrincipal.getUser().getUsername(), allClaims.entrySet());

        return Jwts.builder()
                .claims(allClaims)
                .subject(userPrincipal.getUser().getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 mins
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private  <T>T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validate(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());     // true if expiration date is before current time.
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);     // asks the claims to return expiration time.
    }


}

