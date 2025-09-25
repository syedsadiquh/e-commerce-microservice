package com.gravityer.userservice.services;

import com.gravityer.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;
    // Dummy Secret
    private final String SECRET = "DummySecret123qwertyuiopasdfghjklzxcvbnm1234567890";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Boolean validateUsername(String token) {
        String username = getUsername(token);
        return (username != null && userRepository.existsByUsername(username));
    }

    public ArrayList<String> getRoles(String token) {
        Claims claims = extractAllClaims(token);
        ArrayList<String> roles = (ArrayList<String>) claims.get("roles");
        return roles;
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

    public boolean validate(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


}
