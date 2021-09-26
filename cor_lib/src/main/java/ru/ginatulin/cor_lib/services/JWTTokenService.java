package ru.ginatulin.cor_lib.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ginatulin.cor_lib.interfacces.ITokenService;
import ru.ginatulin.cor_lib.models.UserInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JWTTokenService implements ITokenService {
    @Value("$(jwt.secret)")
    private String JWT_SECRET;

    @Override
    public String generateToken(UserInfo user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.DAYS);
        Date expirationDate = Date.from(expirationTime);
        String compactTokenString = Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getUserEmail())
                .claim("roles", user.getRole())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.ES512, JWT_SECRET)
                .compact();
        return "Bearer " + compactTokenString;
    }

    @Override
    public UserInfo parseToken(String token) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);
        String email = jwsClaims.getBody().getSubject();
        String id = jwsClaims.getBody().get("id", String.class);
        List<String> roles = jwsClaims.getBody().get("roles", List.class);
        return UserInfo.builder()
                .id(UUID.fromString(id))
                .userEmail(email)
                .role(roles)
                .build();
    }
}
