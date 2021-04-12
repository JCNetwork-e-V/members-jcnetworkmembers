package com.jcnetwork.members.security.service;

import com.jcnetwork.members.security.model.Account;
import com.jcnetwork.members.security.model.AccountRole;
import com.jcnetwork.members.security.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiTokenService {

    @Value("${security.api.tokenLifetime}")
    private int tokenLifetime;

    @Value("${security.api.tokenSigningKey}")
    private String tokenSigningKey;

    public String generateToken(User user) {

        Instant expirationTime = Instant.now().plus(tokenLifetime, ChronoUnit.MINUTES);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(tokenSigningKey.getBytes());

        String compactTokenString = Jwts.builder()
                .claim("sub", user.getAccount().getUsername())
                .claim("roles", user.getRoles())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + compactTokenString;
    }

    public User parseToken(String token) {

        byte[] secretBytes = tokenSigningKey.getBytes();

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);

        String username = jwsClaims.getBody().getSubject();

        Set<AccountRole> roles = Arrays.asList(jwsClaims
                .getBody().get("roles").toString()
                .replace("[{", "")
                .replace("}]", "")
                .replace("{", "")
                .split("},")).stream()
                .map(r -> new AccountRole(r.substring(r.indexOf("roleName=") + 9)))
                .collect(Collectors.toSet());

        User apiUser = new User();
        apiUser.setAccount(new Account());
        apiUser.getAccount().setUsername(username);
        apiUser.setRoles(roles);

        return apiUser;
    }
}
