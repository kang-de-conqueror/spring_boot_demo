package com.example.demo.service;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String getUsernameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    String generateToken(String username);

    Boolean validateToken(String token, String username);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
}
