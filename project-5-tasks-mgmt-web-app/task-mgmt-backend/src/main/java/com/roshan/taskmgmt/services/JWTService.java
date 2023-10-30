package com.roshan.taskmgmt.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;


    // extract all claims from jwt token payload
    public Claims extractAllClaims(String token) throws JwtException {
        try {
            return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Expired JWT token");
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed JWT token");
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token");
        } catch (Exception e) {
            throw new JwtException("Unknown JWT exception");
        }
    }

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
    }

    // using the standard claims: "subject"
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW1vQGRlbW8uY29tIiwiaWF0IjoxNjk4NjQ4NzA4LCJleHAiOjE2OTg3MzUxMDh9.eOp8L2Lj8ySDiRkRVZmH_APUgn0FXvvc22TJXu06p8I
    public String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))//* 1000))
//                .encryptWith(getSignKey(), Jwts.ENC.A128CBC_HS256)
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // hopefully the above is equivalent of this
                .compact();
    }

    public String generateToken(String username) {
        return createToken(new HashMap<>(), username);
    }
}