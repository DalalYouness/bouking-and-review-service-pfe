package com.dalal.boukingandreviewservicepfe.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${public-key}")
    private String publicKeyString;

    /**
     * Converts the Base64 encoded RSA Public Key string into a PublicKey object.
     */
    public PublicKey getPublicKeyFromString() throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyString.trim());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the user ID safely to avoid NumberFormatException / ClassCastException
     * during JSON deserialization.
     */
    public Long extractId(String token) throws Exception {
        return extractClaim(token, claims -> {
            Object id = claims.get("id");
            if (id instanceof Number number) {
                return number.longValue();
            }
            return null;
        });
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) throws Exception {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    public <R> R extractClaim(String token, Function<Claims, R> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * NOTE ON TOKEN VALIDATION:
     *
     * I explicitly omitted a custom `isTokenValid()` or `isTokenExpired()` method here.
     *
     * Reason:
     * In this stateless microservice architecture, JJWT's `parseClaimsJws(token)` automatically
     * handles the full verification pipeline during `extractAllClaims()`:
     * 1. Signature Integrity: Validates the token signature against our RSA Public Key.
     * 2. Expiration Check: Verifies the `exp` claim against current system time (throws ExpiredJwtException).
     * 3. Malformed Inspection: Ensures structural validity (throws MalformedJwtException).
     *
     * If `extractAllClaims(token)` succeeds without throwing an exception, the token is
     * mathematically guaranteed to be valid, untampered, and unexpired.
     */
    private Claims extractAllClaims(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKeyFromString())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}