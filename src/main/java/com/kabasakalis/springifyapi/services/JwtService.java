package com.kabasakalis.springifyapi.services;

import com.kabasakalis.springifyapi.models.SpringifyUser;
import com.kabasakalis.springifyapi.repositories.UserRepository;
import com.kabasakalis.springifyapi.security.SecretKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@Component
public class JwtService {
    private static final String ISSUER = "com.srpingify.kabasakalis";
    private static final int EXPIRATION_HOURS = 24;
    private SecretKeyProvider secretKeyProvider;
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    public JwtService() {
        this(null, null);
    }

    @Autowired
    public JwtService(SecretKeyProvider secretKeyProvider,
                      UserRepository userRepository
    ) { this.secretKeyProvider = secretKeyProvider;
        this.userRepository = userRepository;
    }


    public String tokenFor(SpringifyUser user) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now().plusHours(EXPIRATION_HOURS).toInstant(UTC));
        return Jwts.builder()
                .setAudience("Springify Users")
                .setId(user.getEmail())
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Optional<SpringifyUser> verify(String token) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return Optional.ofNullable(userRepository.findByUsername(claims.getBody().getSubject()));
    }
}

