package com.nordeus.jobfair.auctionservice.auctionservice.security.jwt;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.user.UserId;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${security.jwt-secret}")
    private String jwtSecret;

    /**
     * Creates a JWT token with the provided userId
     *
     * @param userId UserId object to be placed in the payload
     * @return Signed JWT token
     */
    public String createUserToken(UserId userId) {
        SecretKey key = this.getSigningKey();
        return Jwts.builder().claim("userId", userId).signWith(key).compact();
    }

    /**
     * Returns the UserId from a JWT token
     * Should generalize to support more claims, however for this use case, this is sufficient
     *
     * @param token JWT token
     * @return UserId object
     * @throws SignatureException if the jwt token is invalid / has been tampered with
     */
    public UserId getUserIdFromToken(String token) throws SignatureException {
        SecretKey key = this.getSigningKey();
        String userId = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("userId").toString();
        return new UserId(UUID.fromString(userId));
    }

    /**
     * Returns a SecretKey object created with the secret key in ENV
     *
     * @return SecretKey object for signing JWTs
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
