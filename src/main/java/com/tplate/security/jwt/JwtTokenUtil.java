package com.tplate.security.jwt;

import com.tplate.security.SecurityConstants;
import com.tplate.layers.persistence.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(SecurityConstants.JWT_AUTHORITIES_KEY,
                user.getRole().getPermissions()
                        .stream()
                        .map(s -> new SimpleGrantedAuthority(s.getAuthority()))
                        .collect(Collectors.toList()));
        claims.put(SecurityConstants.JWT_USER_ID, user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(SecurityConstants.JWT_EXPIRATION_MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token);

        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(SecurityConstants.JWT_HEADER_AUTHORIZATION_KEY);
        if (bearerToken != null && bearerToken.startsWith(SecurityConstants.JWT_TOKEN_BEAR_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
