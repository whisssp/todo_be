package com.todo_quqo.jwt;

import com.todo_quqo.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    private final UserRepository userRepository;

    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private enum ClaimsKey {
        AUTHORITY
    }

    private final Long JWT_EXPIRATION = 36000000L;

    private final String JWT_SECRET = "aisjdfnpaisjdnfpasdpfasnvapsdoiasdnovnasodjnvoasd";

    private final Long TOKEN_REMEMBER_ME_EXPIRATION = 2592000L;

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(ClaimsKey.AUTHORITY.name()).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        log.debug(">>>>>TOKEN: {}", token);
        User principal = new User(claims.getSubject(), "", authorities);
        log.debug(principal.getUsername());

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateToken(Authentication authentication, Boolean rememberMe, String extraAuthorities) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Date expiration = getExpiration(rememberMe); // Get expiration based on User decide to remember or not
        com.todo_quqo.entity.User user = userRepository.findUserByEmail(authentication.getName()).get();
        JwtBuilder builder = Jwts.builder().subject(String.valueOf(user.getId()));

        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimsKey.AUTHORITY.name(), authorities);
        return builder
                .issuedAt(new Date())
                .expiration(expiration)
                .claims(claims)
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    // Get information (subject - UserID) from token
    public String getSubject(String token) {
        Claims claims = getClaims(token);
        return (claims.getSubject());
    }

    public boolean validateToken(String authToken, HttpServletRequest request, HttpServletResponse response) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }

    private SecretKey getSecretKey() {
        System.out.println(JWT_SECRET);
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }

    private Date getExpiration(Boolean rememberMe) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        if (rememberMe) {
            expiryDate = new Date(now.getTime() + TOKEN_REMEMBER_ME_EXPIRATION);
        }
        return expiryDate;
    }
}